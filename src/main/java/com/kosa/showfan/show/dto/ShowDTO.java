package com.kosa.showfan.show.dto;

import java.sql.Date;
import java.util.List;

import com.kosa.showfan.review.dto.ReviewResponseDTO;

import lombok.Data;

@Data
public class ShowDTO {
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
	private List<ReviewResponseDTO> showReveiws;
	private String showPoster;
}
