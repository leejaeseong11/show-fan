package com.kosa.showfan.artist.dao;

import com.kosa.showfan.artist.dto.MyArtistDTO;
import com.kosa.showfan.exception.FindException;

import java.util.List;

public interface MyArtistDAO {
    /**
     * 회원이 선호하는 아티스트 검색
     *
     * @param memberId
     * @return 아티스트 리스트
     * @throws FindException
     */
    List<MyArtistDTO> selectAllByMemberId(Long memberId) throws FindException;
}
