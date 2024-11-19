package com.ssafy.fitpass.auth;

import com.ssafy.fitpass.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {

        Map<String, String> map = new HashMap<>();

        try {
            boolean result = authService.signup(user);
            if(result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> tokens = authService.login(user);
        if (tokens != null) {
            map.put("msg", "success");
            map.put("access-token", tokens.get("accessToken"));
            map.put("refresh-token", tokens.get("refreshToken"));
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("/logout")
    public void logout() {

    }
}
