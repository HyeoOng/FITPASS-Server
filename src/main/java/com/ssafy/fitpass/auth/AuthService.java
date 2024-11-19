package com.ssafy.fitpass.auth;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;

import java.util.Map;

public interface AuthService {
    boolean signup(User user);
    Map<String, String> login(User user);

}
