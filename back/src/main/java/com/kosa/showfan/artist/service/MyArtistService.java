package com.kosa.showfan.artist.service;

import com.kosa.showfan.artist.dao.MyArtistDAO;
import com.kosa.showfan.artist.dao.MyArtistDAOImpl;
import com.kosa.showfan.artist.dto.MyArtistDTO;
import com.kosa.showfan.exception.FindException;

import java.util.List;

public class MyArtistService {
    private static final MyArtistService instance = new MyArtistService();
    private MyArtistDAO myArtistDAO = MyArtistDAOImpl.getInstance();

    public static MyArtistService getInstance() {
        return instance;
    }

    public List<MyArtistDTO> selectById(Long memberId) throws FindException {
        return myArtistDAO.selectAllByMemberId(memberId);
    }
}
