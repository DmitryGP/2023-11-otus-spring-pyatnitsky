package org.dgp.hw.service;

import org.dgp.hw.domain.DevelopedFilm;
import org.dgp.hw.domain.RawFilm;

public interface FilmDevelopingService {

    DevelopedFilm develop(RawFilm film);
}
