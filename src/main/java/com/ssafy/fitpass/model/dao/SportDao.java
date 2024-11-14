package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.Sport;

import java.util.List;

public interface SportDao {
    /**
     * 스포츠를 등록하는 메서드입니다.
     *
     * @param sport
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    int insertSport(Sport sport);

    /**
     * 스포츠 정보를 수정하는 메서드입니다.
     *
     * @param sport
     * @return 수정된 행 수 (수정 성공 : 1)
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
     * @return 삭제된 행 수 (수정 성공 : 1)
     */
    int deleteSport(int sportCode);
}
