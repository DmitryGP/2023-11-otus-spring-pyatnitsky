package org.dgp.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Photo {

    private String number;

    private float exposureDelta;

    private boolean defected;
}
