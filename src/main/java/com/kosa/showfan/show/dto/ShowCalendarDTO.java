package com.kosa.showfan.show.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShowCalendarDTO {
	private String showId;
	private Integer genreId;
	private String showName;
	private String showVenues;
	private String showAddress;
	private String showStartDay;
	private String showEndDay;
	
	private List<String> artistList;
}
