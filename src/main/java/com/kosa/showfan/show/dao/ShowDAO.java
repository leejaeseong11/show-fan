package com.kosa.showfan.show.dao;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.show.dto.*;

import java.util.List;

public interface ShowDAO {
    List<ShowAllInfoDTO> selectById(String id) throws Exception;

    List<ShowDTO> selectAll() throws Exception;

    /**
     * 공연명 검색 시 공연 갯수
     *
     * @param value
     * @return
     */
    Integer selectByStringCnt(String value) throws FindException;

    /**
     * 공연명 검색
     *
     * @param value
     * @return
     * @throws FindException
     */
    List<ShowSearchDTO> selectByString(String value, int startRn, int endRn) throws FindException;

    /**
     * 캘린더에서 공연일정 조회
     *
     * @return
     * @throws FindException
     */
    List<ShowCalendarDTO> selectByDate(String yymm) throws FindException;

    List<ShowGenreDTO> selectByConcept(Long genreId, int startRn, int endRn) throws Exception;

    String selectByName(String name) throws FindException;

    /**
     * 찜 목록 추가
     *
     * @param myShowDTO
     * @return
     * @throws AddException
     */
    String insertMyShow(MyShowDTO myShowDTO) throws AddException;

    /**
     * 찜 목록 삭제
     *
     * @param myShowDTO
     * @return
     * @throws RemoveException
     */
    String deleteMyShow(MyShowDTO myShowDTO) throws RemoveException;

    /**
     * 회원의 찜 목록 검색
     *
     * @param memberId
     * @return 작품 리스트
     * @throws FindException
     */
    List<MyShowDTO> selectMyShowByMemberId(Long memberId) throws FindException;
}
