package org.dgp.hw.service;

import org.dgp.hw.domain.DevelopedFilm;
import org.dgp.hw.domain.PhotoBundle;

public interface PhotoPrintService {

    PhotoBundle print(DevelopedFilm film);
}
