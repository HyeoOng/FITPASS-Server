package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;

import java.util.List;

public interface FriendService {
    boolean sendFriendRequest(int from, int to);
    List<RetUser> getFriends(int userId);
    List<RetUser> getFriendRequests(int userId);
    boolean deleteFriend(int from, int to);
}
