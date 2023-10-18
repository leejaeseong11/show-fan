package com.kosa.showfan.show.dto;

import lombok.Data;

@Data
public class ShowSearchDTO {
	private String showId;
	private String showPoster;
	private Long genreId;
	private String showName;
	private String showVenues;
	private String showStartDay;
	private String showEndDay;
	private String showAddress;
	private String showStatus;

	private Integer reviewCnt;
	private Float gradeAvg;
}
