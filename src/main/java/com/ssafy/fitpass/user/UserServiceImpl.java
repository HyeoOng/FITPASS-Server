package com.ssafy.fitpass.user;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.util.OpenCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

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

        try {
            int result = userDao.insertUser(retUser);
            if (result != 1) {
                throw new RuntimeException("회원 가입 중 오류가 발생했습니다.");
            }

            Map<String, String> info = new HashMap<>();
            info.put("email", user.getEmail());
            info.put("salt", salt);
            userSecuDao.insertInfo(info);
            return true;
        } catch (DataAccessException e) {
            throw new RuntimeException("회원 가입 중 데이터베이스 오류가 발생했습니다.");
        }
    }

    @Override
    public RetUser login(User user) {
        try {
            String salt = userSecuDao.selectSalt(user.getEmail());
            if (salt == null) {
                throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
            }

            String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(user.getPassword(), salt));

            Map<String, String> info = new HashMap<>();
            info.put("email", user.getEmail());
            info.put("password", hashPw);

            RetUser retUser = userDao.login(info);
            if (retUser == null) {
                throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다.");
            }
            return retUser;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("로그인 중 데이터베이스 오류가 발생했습니다.");
        }
    }

    @Override
    public List<RetUser> getAllUsers() {
        try {
            return userDao.selectAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 목록 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    public RetUser getUser(int userId) {
        try {
            return userDao.selectOne(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean modifyUser(User user) {
        try {
            RetUser originalUser = getUser(user.getUserId());
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                user.setEmail(originalUser.getEmail());
            }
            if (user.getNn() == null || user.getNn().isEmpty()) {
                user.setNn(originalUser.getNn());
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(originalUser.getName());
            }
            return userDao.updateUser(user) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean removeUser(int userId) {
        try {
            return userDao.deleteUser(userId) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 삭제 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean getEmail(String email) {
        try {
            return userDao.checkEmail(email) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("이메일 중복 확인 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean getNN(String nickname) {
        try {
            return userDao.checkNn(nickname) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임 중복 확인 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean createProfile(int userId, Photo photo) {
        return false;
    }

    @Override
    public boolean modifyProfile(int userId, Photo photo) {
        return false;
    }

    @Override
    public List<RetUser> getUserByNn(String nn) {
        try {
            nn = "%" + nn + "%";
            return userDao.selectAllByNn(nn);
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임으로 사용자 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    public int getUserId(String nn) {
        try {
            return userDao.selectUserId(nn);
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임으로 사용자 ID 조회 중 오류가 발생했습니다.");
        }
    }
}