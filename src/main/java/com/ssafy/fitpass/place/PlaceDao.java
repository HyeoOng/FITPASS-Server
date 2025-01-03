package com.ssafy.fitpass.place;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaceDao {

    /***
     * 한 사용자(userId)가 방문한 모든 장소 정보를 조회하는 메서드입니다.
     * @param userId
     * @return Place 목록
     */
    public List<Place> selectAll(int userId);

    /***
     * 게시글(postId)에 작성된 장소 정보를 조회하는 메서드입니다.
     * @param postId
     * @return 게시글에 작성된 장소 정보
     */
    public Place selectOne(int postId);

    /***
     * 장소를 등록하는 메서드입니다.
     * @param place
     * @return 등록 성공 : 1
     */
    public int insertPlace(Place place);

    /***
     * placeId를 조회하는 메서드입니다.
     * @param place
     * @return placeId를 반환.
     */
    public Integer selectPlaceId(Place place);

    /***
     * placeId를 통해 상호명을 얻는 메서드입니다.
     * @param placeId
     * @return 상호명
     */
    String selectPlaceName(int placeId);

    /***
     * placeId를 통해 장소 정보를 불러오는 메서드
     * @param placeId
     * @return
     */
    Place selectByid(int placeId);
}
