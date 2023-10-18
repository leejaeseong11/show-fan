package com.kosa.showfan.show.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ShowAllInfoDTO {
	private String showId;
	private Long genreId;
	private String showName;
	private Date showStartDay;
	private Date showEndDay;
	private String showTime;
	private Integer showAge;
	private String showAddress;
	private String showVenues;
	private String showTicketingSite;
	private String showStory;
	private String showImage1;
	private String showImage2;
	private String showImage3;
	private String showImage4;
	private String showStatus;
	private Double showLatitude;
	private Double showLongitude;
	private String showRuntime;
//	private List<ReviewDTO> showReveiws;
	private String showPoster;
	private Long reviewId;
	private Long memberId;
	private Integer reviewGrade;
	private String reviewContent;
	private Date reviewCreatedAt;
	private String reviewIsModified;
	private Long seatId;
	private String seatName;
	private Integer seatPrice;
//	private List<ReplyDTO> reviewReply;
	private Long artistId;
	private String artistName;
	private String artistImage;
}
