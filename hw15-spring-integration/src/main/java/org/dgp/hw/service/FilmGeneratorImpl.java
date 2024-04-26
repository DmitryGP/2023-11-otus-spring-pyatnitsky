package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.domain.Photo;
import org.dgp.hw.domain.PhotoBundle;
import org.dgp.hw.domain.RawFilm;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmGeneratorImpl implements FilmGenerator {

    private static final int[] FRAME_COUNTS = {36, 25, 15};

    private static final String[] NUM_PREFIX = {"A", "B", "C"};

    private final AtomicInteger filmIndex = new AtomicInteger(0);

    private final DevelopingGateway developingGateway;

    private final DefectedPhotosGateway defectedPhotosGateway;

    @Override
    public void startGeneratorLoop() {
        var pool = ForkJoinPool.commonPool();

        for (int i = 0; i < 5; i++) {
            int num = i + 1;

            pool.execute(() -> {
                var film = generateFilm();

                logStartProcess(num, film);

                var photos = developingGateway.process(film);

                logGoodPhotos(num, photos);

                var defectedPhotos = defectedPhotosGateway.getDefectedPhotos();

                logDefectedPhotos(num, defectedPhotos);
            });

            delay();
        }
    }

    private static void logDefectedPhotos(int num, PhotoBundle defectedPhotos) {
        log.atInfo()
                .setMessage("{}. Defected {} photos are: {}")
                .addArgument(num)
                .addArgument(defectedPhotos.getPhotos().size())
                .addArgument(defectedPhotos.getPhotos().stream()
                        .map(Photo::getNumber)
                        .collect(Collectors.joining(", ")))
                .log();
    }

    private static void logGoodPhotos(int num, PhotoBundle photos) {
        log.atInfo()
                .setMessage("{}. New {} good photos: {}")
                .addArgument(num)
                .addArgument(photos.getPhotos().size())
                .addArgument(photos.getPhotos().stream()
                        .map(ph -> "photo %s with %f exposure delta. defected:%s"
                                .formatted(ph.getNumber(), ph.getExposureDelta(), ph.isDefected()))
                        .collect(Collectors.joining(", ")))
                .log();
    }

    private static void logStartProcess(int num, RawFilm film) {
        log.atInfo()
                .setMessage("{}. New raw film: {}")
                .addArgument(num)
                .addArgument("%s with %d frames".formatted(film.getNumber(), film.getFramesCount()))
                .log();
    }

    private RawFilm generateFilm() {
        return RawFilm.builder()
                .number("%s%d".formatted(getPrefix(), filmIndex.getAndAdd(1)))
                .framesCount(getFramesCount())
                .build();
    }

    private static int getFramesCount() {
        var index = ThreadLocalRandom.current().nextInt(0, FRAME_COUNTS.length);
        return FRAME_COUNTS[index];
    }

    private static String getPrefix() {
        var index = ThreadLocalRandom.current().nextInt(0, NUM_PREFIX.length);
        return NUM_PREFIX[index];
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
