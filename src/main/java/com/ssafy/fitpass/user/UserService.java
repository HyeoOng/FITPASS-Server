package com.ssafy.fitpass.user;

import com.ssafy.fitpass.photo.Photo;

import java.util.List;

public interface UserService {

    boolean signup(User user);
    RetUser login(User user);
    boolean isUserExist(String email);
    List<RetUser> getAllUsers();
    RetUser getUser(int userId);
    boolean modifyUser(User user);
    boolean removeUser(int userId);
    boolean getEmail(String email);
    boolean getNN(String nickname);
    boolean createProfile(int userId, Photo photo);
    boolean modifyProfile(int userId, Photo photo);
    List<RetUser> getUserByNn(String nn);
    int getUserId(String nn);
}
