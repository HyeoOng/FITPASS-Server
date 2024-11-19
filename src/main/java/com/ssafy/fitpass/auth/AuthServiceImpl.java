package com.ssafy.fitpass.auth;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;
import com.ssafy.fitpass.user.UserDao;
import com.ssafy.fitpass.user.UserSecuDao;
import com.ssafy.fitpass.util.OpenCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final UserSecuDao userSecuDao;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserDao userDao, UserSecuDao userSecuDao, JwtUtil jwtUtil) {
        this.userDao = userDao;
        this.userSecuDao = userSecuDao;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean signup(User user) {
        String salt = UUID.randomUUID().toString();
        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(user.getPassword(), salt));
        user.setPassword(hashPw);
        int result = userDao.insertUser(user);

        Map<String,String> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("salt", salt);
        userSecuDao.insertInfo(info);
        return result == 1;
    }

    @Override
    public Map<String, String> login(User user) {
        String salt = userSecuDao.selectSalt(user.getEmail());
        String hashPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(user.getPassword(), salt));

        Map<String, String> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("password", hashPw);
        RetUser retUser = userDao.login(info);

        if (retUser != null) {
            String accessToken = jwtUtil.createAccessToken(retUser.getUserId(), retUser.getNn());
            String refreshToken = jwtUtil.createRefreshToken(retUser.getUserId(), retUser.getNn());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }

        return null;
    }

}
