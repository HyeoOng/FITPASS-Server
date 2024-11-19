package com.ssafy.fitpass.user;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.util.OpenCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        String salt = UUID.randomUUID().toString();
        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(user.getPassword(), salt));

        RetUser retUser = new RetUser();
        retUser.setEmail(user.getEmail());
        retUser.setName(user.getName());
        retUser.setPassword(hashPw);
        retUser.setNn(user.getNn());
        retUser.setAdmin(user.getAdmin());

        int result = userDao.insertUser(retUser);

        Map<String,String> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("salt", salt);
        userSecuDao.insertInfo(info);
        return result == 1;
    }

    @Override
    public RetUser login(User user) {
        String salt = userSecuDao.selectSalt(user.getEmail());
        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(user.getPassword(), salt));

        Map<String, String> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("password", hashPw);
        RetUser retUser = userDao.login(info);
        return retUser;

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

    @Override
    public boolean createProfile(int userId, Photo photo) {
        return false;
    }

    @Override
    public boolean modifyProfile(int userId, Photo photo) {
        return false;
    }
}
