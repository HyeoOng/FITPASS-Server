package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    FriendDao friendDao;

    public FriendServiceImpl(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public boolean sendFriendRequest(int from, int to) {
        Integer result = friendDao.selectOne(to, from);  // selectOne 결과를 Integer로 받음
        if (result != null && result == 1) {  // 친구 신청을 받은 상태라면
            return friendDao.updateRequestStatus(from, to) == 1;
        }
        // 친구 신청을 받지 않았다면 2번 요청을 보내게 된다.
        return friendDao.insertRequest(from, to) == 2;
    }

    @Override
    public List<RetUser> getFriends(int userId) {
        return friendDao.selectFriends(userId);
    }

    @Override
    public List<RetUser> getFriendRequests(int userId) {
        return friendDao.selectFriendRequest(userId);
    }
}
