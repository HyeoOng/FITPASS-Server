package com.ssafy.fitpass.user;

import java.util.List;

public interface UserService {

    boolean signup(User user);
    RetUser login(User user);
    List<RetUser> getAllUsers();
    RetUser getUser(int userId);
    boolean modifyUser(User user);
    boolean removeUser(int userId);
    boolean getEmail(String email);
    boolean getNN(String nickname);
    List<RetUser> getAllAdmin();
    boolean modifyAdmin(int userId);
}
