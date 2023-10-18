package com.kosa.showfan.artist.dto;

import lombok.Data;

@Data
public class MyArtistDTO {
    private Long artistId;
    private Long memberId;
    private Integer myArtistViewCount;
    private Double myArtistAvgGrade;
    private String artistName;
    private String artistImage;
}
