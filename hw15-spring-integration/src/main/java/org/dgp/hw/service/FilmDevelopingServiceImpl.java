package org.dgp.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.domain.DevelopedFilm;
import org.dgp.hw.domain.RawFilm;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class FilmDevelopingServiceImpl implements FilmDevelopingService {

    @Override
    public DevelopedFilm develop(RawFilm film) {

        log.atInfo()
                .setMessage("Developing film {} of {} frames")
                .addArgument(film.getNumber())
                .addArgument(film.getFramesCount())
                .log();

        delay();

        var developedFilm = DevelopedFilm.builder()
                .number(film.getNumber())
                .framesCount(film.getFramesCount())
                .exposureDelta(ThreadLocalRandom.current().nextFloat(-1, 1))
                .build();

        log.atInfo()
                .setMessage("Developing film {} is completed")
                .addArgument(film.getNumber())
                .log();

        return developedFilm;
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
