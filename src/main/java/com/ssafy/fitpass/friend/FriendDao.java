package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendDao {

    /***
     *  친구 신청을 보내는 메서드입니다.
     * @param fromUser(친구 신청을 보내는 사람)
     * @param toUser(친구 신청을 받는 사람)
     * @return 등록된 행 수를 반환(정상 등록 : 1)
     */
    public int insertRequest(@Param("fromUser") int fromUser, @Param("toUser") int toUser);

    

    /***
     * 친구 신청을 수락(친구 신청 수락 = status를 false -> true로 변환)하는 메서드입니다.
     * @param currUser (현재 유저)
     * @param toUser (현재 유저에게 요청을 보낸 아이디)
     * @return 변환된 행 수 반환(정상 등록 : 1)
     */
    public int updateRequestStatus(@Param("currUser") int currUser, @Param("toUser") int toUser);

    // 현재 어떤 상태인지 status 값 반환
    // public int selectOne(int from, int to);

    /***
     * 친구 관계인 사람 목록(친구 신청 쌍방 완료)을 조회하는 메서드입니다.
     * @param userId 
     * @return userId와 친구인 모든 사용자 정보 반환
     */
    public List<RetUser> selectFriends(int userId);

    /***
     * 친구 신청을 받은 목록을 조회하는 메서드입니다.
     * @param toUser (= userId)
     * @return 나에게 친구 신청 요청을 보낸 사용자 목록 반환
     */
    public List<RetUser> selectFriendRequest(int toUser);

    /***
     * 내가(from) 다른 사용자(to)에게 친구 신청을 받은 적이 있는지 확인하는 메서드입니다.
     * @param fromUser
     * @param toUser
     * @return 친구 신청을 받았다면 1 아니라면 0을 반환
     */
    public int selectOne(@Param("fromUser") int fromUser, @Param("toUser") int toUser);

    /**
     * 친구 요청을 삭제하는 메서드입니다.
     *
     * @param requestId (요청을 보낸 유저 아이디)
     * @param currUserId (현재 로그인한 유저 아이디)
     * @return 삭제된 행 수 (삭제된 행 수가 2라면 정상 수행)
     */
    int deleteFriendRequest(@Param("requestId") int requestId, @Param("currUserId") int currUserId);

    /**
     * 친구를 삭제하는 메서드입니다.
     *
     * @param fromUser
     * @param toUser
     * @return 삭제된 행 수 (삭제된 행 수가 2라면 정상 수행)
     */
    int deleteFriend(@Param("fromUser") int fromUser, @Param("toUser") int toUser);
}
