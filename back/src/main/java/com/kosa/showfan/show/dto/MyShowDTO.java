package com.kosa.showfan.show.dto;

import lombok.Data;

@Data
public class MyShowDTO {
    private Long memberId;
    private String showId;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }
}
