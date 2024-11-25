package com.ssafy.fitpass.user.service;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoDao;
import com.ssafy.fitpass.user.dao.UserDao;
import com.ssafy.fitpass.user.dao.UserSecuDao;
import com.ssafy.fitpass.user.dto.LoginUserDto;
import com.ssafy.fitpass.user.dto.PutUserDto;
import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.dto.SignupUserDto;
import com.ssafy.fitpass.user.entity.User;
import com.ssafy.fitpass.util.OpenCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserSecuDao userSecuDao;
    private final LoginAttemptService loginAttemptService;
    private final PhotoDao photoDao;

    public UserServiceImpl(UserDao userDao, UserSecuDao userSecuDao, PhotoDao photoDao
            , LoginAttemptService loginAttemptService) {
        this.userDao = userDao;
        this.userSecuDao = userSecuDao;
        this.loginAttemptService = loginAttemptService;
        this.photoDao = photoDao;
    }

    @Override
    public boolean signup(SignupUserDto signupUserDto) {
        String salt = UUID.randomUUID().toString();
        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(signupUserDto.getPassword(), salt));

        User user = new User();
        user.setEmail(signupUserDto.getEmail());
        user.setName(signupUserDto.getName());
        user.setPassword(hashPw);
        user.setNn(signupUserDto.getNn());
        user.setAdmin(0);

        try {
            int result = userDao.insertUser(user);
            if (result != 1) {
                throw new RuntimeException("회원 가입 중 오류가 발생했습니다."); // RegDBException - DB PK 오류
            }

            Map<String, String> info = new HashMap<>();
            info.put("email", user.getEmail());
            info.put("salt", salt);
            try{
                userSecuDao.insertInfo(info);
            } catch (DataAccessException e) {
                throw new RuntimeException("salt 삽입 중 DB 오류 발생"); // RegDBException
            }

            return true;
        } catch (DataAccessException e) {
            throw new RuntimeException("회원 가입 중 데이터베이스 오류가 발생했습니다."); // RegDBException - DB 에러
        }
    }

    // 로그인 처리 로직을 서비스 계층으로 이동
    @Override
    public RetUser handleLogin(LoginUserDto loginUserDto) {
        String email = loginUserDto.getEmail();

        // 1. 이메일 존재 여부 확인
        if (!isUserExist(email)) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다."); // UserException - NF
        }

        // 2. 로그인 시도 횟수 초과로 차단된 사용자 확인
        if (loginAttemptService.isBlocked(email)) {
            throw new IllegalArgumentException("로그인 시도 횟수 초과로 인해 차단되었습니다."); // UserException - TC
        }

        // 3. 비밀번호 체크
        String salt = userSecuDao.selectSalt(loginUserDto.getEmail());
        if (salt == null) {
            throw new IllegalArgumentException("이메일이 존재하지 않습니다."); // UserException - NF
        }

        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(loginUserDto.getPassword(), salt));

        Map<String, String> info = new HashMap<>();
        info.put("email", loginUserDto.getEmail());
        info.put("password", hashPw);

        User user = userDao.login(info);
        if (user == null) {
            loginAttemptService.increaseAttempts(email);  // 로그인 실패 시 시도 횟수 증가
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다."); // UserException - NF
        }

        loginAttemptService.resetAttempts(email); // 로그인 성공 시 시도 횟수 초기화
        return convertToDto(user);
    }

    @Override
    public boolean isUserExist(String email) {
        try {
            return userDao.selectUserByEmail(email) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("이메일 존재 여부 확인 중 데이터베이스 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public List<RetUser> getAllUsers() {
        try {
            List<User> users = userDao.selectAll();
            return users.stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 목록 조회 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public RetUser getUser(int userId) {
        try {
            User user = userDao.selectOne(userId);
            return convertToDto(user);
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 조회 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean modifyUser(PutUserDto putUserDto) {
        try {
            RetUser originalUser = getUser(putUserDto.getUserId());
            if (originalUser == null) {
                throw new IllegalArgumentException("존재하지 않는 사용자입니다."); // UserException - NF
            }
            User user = new User();
            user.setUserId(originalUser.getUserId()); // 기존 ID 유지
            if (putUserDto.getEmail() == null || putUserDto.getEmail().isEmpty()) {
                user.setEmail(originalUser.getEmail());
            }
            if (putUserDto.getNn() == null || putUserDto.getNn().isEmpty()) {
                user.setNn(originalUser.getNn());
            }
            if (putUserDto.getName() == null || putUserDto.getName().isEmpty()) {
                user.setName(originalUser.getName());
            }
            return userDao.updateUser(user) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 수정 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean removeUser(int userId) {
        try {
            return userDao.deleteUser(userId) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사용자 삭제 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean getEmail(String email) {
        try {
            return userDao.checkEmail(email) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("이메일 중복 확인 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean getNN(String nickname) {
        try {
            return userDao.checkNn(nickname) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임 중복 확인 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean createProfile(int userId, Photo photo) {
        try {
            return photoDao.insertProfile(userId, photo)==1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사진 등록 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public boolean modifyProfile(int userId, Photo photo) {
        try {
            return photoDao.updateProfile(userId, photo)==1;
        } catch (DataAccessException e) {
            throw new RuntimeException("사진 수정 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public List<RetUser> getUserByNn(String nn) {
        try {
            nn = "%" + nn + "%";
            List<User> users = userDao.selectAllByNn(nn);
            return users.stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임으로 사용자 조회 중 오류가 발생했습니다."); // RegDBException
        }
    }

    @Override
    public int getUserId(String nn) {
        try {
            return userDao.selectUserId(nn);
        } catch (DataAccessException e) {
            throw new RuntimeException("닉네임으로 사용자 ID 조회 중 오류가 발생했습니다."); // RegDBException
        }
    }

    private RetUser convertToDto(User user) {
        if (user == null) return null;

        return new RetUser(
            user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getNn(),
                user.getRegDate(),
                user.getAdmin(),
                user.getProfile()
        );
    }
}