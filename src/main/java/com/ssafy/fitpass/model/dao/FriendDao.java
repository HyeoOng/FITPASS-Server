package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.User;

import java.util.List;

public interface FriendDao {

    /***
     *  친구 신청을 보내는 메서드입니다.
     * @param from(친구 신청을 보내는 사람)
     * @param to(친구 신청을 받는 사람)
     * @return 등록된 행 수를 반환(정상 등록 : 1)
     */
    public int insertReqeust(int from, int to);

    

    /***
     * 친구 신청을 수락(친구 신청 수락 = status를 false -> true로 변환)하는 메서드입니다.
     * @param requestId (친구 신청 받은 요청의 Id값)
     * @return 변환된 행 수 반환(정상 등록 : 1)
     */
    public int updateRequestStatue(int requestId);

    // 현재 어떤 상태인지 status 값 반환
    // public int selectOne(int from, int to);

    /***
     * 친구 관계인 사람 목록(친구 신청 쌍방 완료)을 조회하는 메서드입니다.
     * @param userId 
     * @return userId와 친구인 모든 사용자 정보 반환
     */
    public List<User> selectFriends(int userId);

    /***
     * 친구 신청을 받은 목록을 조회하는 메서드입니다.
     * @param to (= userId)
     * @param status (=false인 사람 조회)
     * @return 나에게 친구 신청 요청을 보낸 사용자 목록 반환
     */
    public List<User> selectFriendRequest(int to, int status);
}
