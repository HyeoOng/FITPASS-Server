package com.ssafy.fitpass.user.service;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.user.dto.LoginUserDto;
import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.dto.SignupUserDto;
import com.ssafy.fitpass.user.entity.User;

import java.util.List;

public interface UserService {

    boolean signup(SignupUserDto user);
    // RetUser login(LoginUserDto user);
    RetUser handleLogin(LoginUserDto user);
    boolean isUserExist(String email);
    List<User> getAllUsers();
    User getUser(int userId);
    boolean modifyUser(User user);
    boolean removeUser(int userId);
    boolean getEmail(String email);
    boolean getNN(String nickname);
    boolean createProfile(int userId, Photo photo);
    boolean modifyProfile(int userId, Photo photo);
    List<RetUser> getUserByNn(String nn);
    int getUserId(String nn);

    User login(User user);
}
