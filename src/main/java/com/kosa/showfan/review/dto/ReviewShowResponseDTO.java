package com.kosa.showfan.review.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewShowResponseDTO {
    private Long reviewId;
    private String showId;
    private String showPoster;
    private String showName;
    private String showVenues;
    private Long genreId;
    private Float reviewGrade;
    private String reviewContent;
    private Date reviewCreatedAt;
    private String reviewIsModified;
    private String seatName;
    private Integer seatPrice;

}
