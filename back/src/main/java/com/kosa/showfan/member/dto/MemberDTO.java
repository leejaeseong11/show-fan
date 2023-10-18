package com.kosa.showfan.member.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPwd;
    private String memberNickname;
    private String memberImage;
    private String memberEmailAlert;
    private Date memberCreatedAt;
    private Date memberDeletedAt;
    private Integer memberTotalPrice;
    private Integer memberStatusId;
	
    public String getMemberPwd() {
		return this.memberPwd;
	}
}
