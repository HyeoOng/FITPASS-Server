package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.dto.RetUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;

    public FriendServiceImpl(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public boolean sendFriendRequest(int from, int to) {
        // from_user = from, to_user = toUser, status = 0인 행 개수 반환
        // select문에서 count로 해당되는 행 수 반환하도록 설정 -> 처음 테이블이 생성되어 값이 없을 경우(0) 대비
        int result = friendDao.selectOne(from, to);

        if (result == 1) {  // 친구 신청을 받은 상태라면
            return friendDao.updateRequestStatus(from, to) == 1;
        }
        // 친구 신청을 받지 않았다면 2개 행 삽입
        return friendDao.insertRequest(from, to) == 2;
    }

    @Override
    public List<RetUser> getFriends(int userId) {
        // inner join 2번 쓰는 것으로 변경
        return friendDao.selectFriends(userId);
    }

    @Override
    public List<RetUser> getFriendRequests(int userId) {
        return friendDao.selectFriendRequest(userId);
    }

    @Override
    public boolean deleteFriendRequest(int requestId, int currUserId) {
        System.out.println("requestId " + requestId);
        return friendDao.deleteFriendRequest(requestId, currUserId) == 2;
    }

    @Override
    public boolean deleteFriend(int from, int to) {
        return friendDao.deleteFriend(from, to) == 2;
    }
}
