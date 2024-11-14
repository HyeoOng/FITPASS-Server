package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.Place;

import java.util.List;

public interface PlaceDao {

    /***
     * 한 사용자(userId)가 방문한 모든 장소 정보 조회
     * @param userId
     * @return Place 목록
     */
    public List<Place> selectAll(int userId);



    /***
     * 게시글(postId)에 작성된 장소 정보 조회
     * @param postId
     * @return 게시글에 작성된 장소 정보
     */
    public Place selectOne(int postId);
}
