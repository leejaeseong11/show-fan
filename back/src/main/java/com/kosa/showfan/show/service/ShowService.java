package com.kosa.showfan.show.service;

import java.util.List;

import com.kosa.showfan.exception.AddException;
import com.kosa.showfan.exception.FindException;
import com.kosa.showfan.exception.RemoveException;
import com.kosa.showfan.show.dao.ShowDAO;
import com.kosa.showfan.show.dao.ShowDAOImpl;
import com.kosa.showfan.show.dto.MyShowDTO;
import com.kosa.showfan.show.dto.ShowAllInfoDTO;
import com.kosa.showfan.show.dto.ShowCalendarDTO;
import com.kosa.showfan.show.dto.ShowDTO;
import com.kosa.showfan.show.dto.ShowGenreDTO;
import com.kosa.showfan.show.dto.ShowListDTO;
import com.kosa.showfan.show.dto.ShowSearchDTO;

public class ShowService {
    private static final ShowService instance = new ShowService();
    private ShowDAO showDAO = ShowDAOImpl.getInstance();

    public static ShowService getInstance() {
        return instance;
    }

    public List<ShowDTO> selectAll() throws Exception {
        return showDAO.selectAll();
    }

    public ShowListDTO search(String value, int page) throws FindException {

        int showCnt = 20;                        // 1	2	3	4
        int endRn = showCnt * page;                // 20	40	60	80
        int startRn = (endRn - showCnt) + 1;    // 1	21	41	61

        int findShowCnt = showDAO.selectByStringCnt(value);
        List<ShowSearchDTO> list = showDAO.selectByString(value, startRn, endRn);

        return new ShowListDTO<>(findShowCnt, list);
    }

    public ShowListDTO calendar(String yymm) throws FindException {
        List<ShowCalendarDTO> list = showDAO.selectByDate(yymm);
        return new ShowListDTO<>(list.size(), list);
    }

    public List<ShowAllInfoDTO> selectByShowId(String showId) throws Exception {
        return showDAO.selectById(showId);
    }

    public List<ShowGenreDTO> selectByConcept(Long genreId, int page) throws Exception {
    	
    	int showCnt = 30;                       
        int endRn = showCnt * page;             
        int startRn = (endRn - showCnt) + 1;    
        
        return showDAO.selectByConcept(genreId, startRn, endRn);
    }

    public String selectByName(String name) throws FindException {
        return showDAO.selectByName(name);
    }

    public String insertMyShow(MyShowDTO myShowDTO) throws AddException {
        return showDAO.insertMyShow(myShowDTO);
    }

    public String deleteMyShow(MyShowDTO myShowDTO) throws RemoveException {
        return showDAO.deleteMyShow(myShowDTO);
    }

    public List<MyShowDTO> selectMyShowByMemberId(Long memberId) throws FindException {
        return showDAO.selectMyShowByMemberId(memberId);
    }
}
