package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.Sport;

import java.util.List;

public interface SportDao {
    /**
     * 스포츠를 저장하는 메서드입니다.
     *
     * @param sport
     * @return 결과
     */
    int insertSport(Sport sport);

    /**
     * 스포츠 정보를 수정하는 메서드입니다.
     *
     * @param sport
     * @return 결과
     */
    int updateSport(Sport sport);

    /**
     * 모든 스포츠를 조회하는 메서드입니다.
     *
     * @return 모든 스포츠 정보
     */
    List<Sport> selectAll();

    /**
     * 스포츠를 삭제하는 메서드입니다.
     *
     * @param sportCode
     * @return 결과
     */
    int deleteSport(int sportCode);
}
