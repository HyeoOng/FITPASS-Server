package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.user.dto.RetUser;

import java.util.List;

public interface FriendService {
    boolean sendFriendRequest(int from, int to);
    List<RetUser> getFriends(int userId);
    List<RetUser> getFriendRequests(int userId);
    boolean deleteFriendRequest(int requestId, int currUserId);
    boolean deleteFriend(int from, int to);
}
