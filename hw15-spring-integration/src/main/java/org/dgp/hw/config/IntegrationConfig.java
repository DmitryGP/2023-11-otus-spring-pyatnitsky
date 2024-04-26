package org.dgp.hw.config;

import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.domain.Photo;
import org.dgp.hw.domain.PhotoBundle;
import org.dgp.hw.service.FilmDevelopingService;
import org.dgp.hw.service.PhotoPrintService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

import java.util.List;


@Configuration
@Slf4j
public class IntegrationConfig {

    @Bean
    public MessageChannel developingChannel() {
        return MessageChannels.queue(10).getObject();
    }

    @Bean
    public MessageChannel photoChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public MessageChannel defectedPhotosChannel() {
        return MessageChannels.queue().getObject();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow filmToPhotoFlow(FilmDevelopingService filmDevelopingService,
                                           PhotoPrintService photoPrintService) {

        return IntegrationFlow.from(developingChannel())
                .handle(filmDevelopingService, "develop")
                .handle(photoPrintService, "print")
                .splitWith(s -> s.applySequence(false)
                        .function(IntegrationConfig::splitBundle))
                .<PhotoBundle, Boolean>route(phb -> phb.getComment().contains("defected"),
                        mapping -> mapping.channelMapping(true, "defectedPhotosChannel")
                                .channelMapping(false, "photoChannel"))

                .get();
    }

    private static List<PhotoBundle> splitBundle(PhotoBundle phb) {
        var goodPhotos = phb.getPhotos().stream()
                .filter(ph -> !ph.isDefected())
                .toList();

        var defectedPhotos = phb.getPhotos().stream()
                .filter(Photo::isDefected)
                .toList();

        return List.of(PhotoBundle.builder()
                        .photos(goodPhotos)
                        .comment(phb.getComment() + " good photos")
                        .build(),
                PhotoBundle.builder()
                        .photos(defectedPhotos)
                        .comment(phb.getComment() + " defected photos")
                        .build());
    }

}
