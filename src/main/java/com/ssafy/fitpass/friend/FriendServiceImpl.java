package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.User;

import java.util.List;

public class FriendServiceImpl implements FriendService {

    FriendDao friendDao;

    public FriendServiceImpl(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public boolean sendFriendRequest(int from, int to) {
        if(friendDao.selectOne(from, to)==1){ // 친구 신청을 받은 상태라면
            return friendDao.updateRequestStatus(from ,to)==1;
        }
        // 친구 신청을 받지 않았다면 .. 2번 요청을 보내게 된다.
        return friendDao.insertRequest(from, to)==2;
    }

    @Override
    public List<User> getFriends(int userId) {
        return friendDao.selectFriends(userId);
    }

    @Override
    public List<User> getFriendRequests(int userId) {
        return friendDao.selectFriendRequest(userId);
    }
}
