package org.dgp.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.domain.DevelopedFilm;
import org.dgp.hw.domain.Photo;
import org.dgp.hw.domain.PhotoBundle;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
@Service
public class PhotoPrintServiceImpl implements PhotoPrintService {
    @Override
    public PhotoBundle print(DevelopedFilm film) {

        log.atInfo()
                .setMessage("Printing film {} of {} frames")
                .addArgument(film.getNumber())
                .addArgument(film.getFramesCount())
                .log();

        var photos = IntStream.range(0, film.getFramesCount())
                .mapToObj(i -> printPhotos(film, i)).toList();

        log.atInfo()
                .setMessage("Printing film {} is completed")
                .addArgument(film.getNumber())
                .log();

        return PhotoBundle.builder()
                .photos(photos)
                .comment("Film %s".formatted(film.getNumber()))
                .build();
    }

    private Photo printPhotos(DevelopedFilm film, int i) {
        var photo = Photo.builder()
                .number("%s_%d".formatted(film.getNumber(), i))
                .exposureDelta(getExposureDelta(film))
                .defected(isDefected())
                .build();
        delay();

        return photo;
    }

    private static float getExposureDelta(DevelopedFilm film) {
        return film.getExposureDelta() +
                ThreadLocalRandom.current().nextFloat(-1, 1);
    }

    private static boolean isDefected() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
