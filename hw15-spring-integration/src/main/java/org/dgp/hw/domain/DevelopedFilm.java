package org.dgp.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DevelopedFilm {

    private String number;

    private int framesCount;

    private float exposureDelta;
}
