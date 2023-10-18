package com.kosa.showfan.show.dto;

import java.util.List;

public class ShowListDTO<T> {
	private Integer showCnt;
	private List<T> show;
	
	public ShowListDTO(Integer size, List<T> show) {
		this.showCnt = size;
		this.show = show;
	}	
}
