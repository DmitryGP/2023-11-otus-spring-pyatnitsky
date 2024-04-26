package org.dgp.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PhotoBundle {

    private String comment;

    private List<Photo> photos;

}
