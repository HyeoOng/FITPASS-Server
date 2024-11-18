package com.ssafy.fitpass.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserSecuDao userSecuDao;

    public UserServiceImpl(UserDao userDao, UserSecuDao userSecuDao) {
        this.userDao = userDao;
        this.userSecuDao = userSecuDao;
    }

    @Override
    public boolean signup(User user) {
        return false;
    }

    @Override
    public RetUser login(User user) {
        return null;
    }

    @Override
    public List<RetUser> getAllUsers() {
        return userDao.selectAll();
    }

    @Override
    public RetUser getUser(int userId) {
        return userDao.selectOne(userId);
    }

    @Override
    public boolean modifyUser(User user) {
        return userDao.updateUser(user) == 1;
    }

    @Override
    public boolean removeUser(int userId) {
        return userDao.deleteUser(userId) == 1;
    }

    @Override
    public boolean getEmail(String email) {
        return userDao.checkEmail(email) == 1;
    }

    @Override
    public boolean getNN(String nickname) {
        return userDao.checkNn(nickname) == 1;
    }

    @Override
    public List<RetUser> getAllAdmin() {
        return userDao.selectAllAdmin();
    }

    @Override
    public boolean modifyAdmin(int userId) {
        return userDao.updateAdmin(userId) == 1;
    }
}