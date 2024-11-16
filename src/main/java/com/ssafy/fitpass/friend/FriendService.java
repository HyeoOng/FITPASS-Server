package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.User;

import java.util.List;

public interface FriendService {
    boolean sendFriendRequest(int from, int to);
    List<User> getFriends(int userId);
    List<User> getFriendRequests(int userId);

}
