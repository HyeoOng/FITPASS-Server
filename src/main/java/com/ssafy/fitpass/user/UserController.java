package com.ssafy.fitpass.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {

        Map<String, String> map = new HashMap<>();

        try {
            boolean result = userService.signup(user);
            if(result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        } catch (IllegalArgumentException e) {
            map.put("msg", e.getMessage());
        } catch (Exception e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        RetUser loginedUser = userService.login(user);
        if (loginedUser != null) {
            request.getSession().setAttribute("user", loginedUser);
            map.put("msg", "success");
            map.put("userId", loginedUser.getUserId());
            map.put("nickname", loginedUser.getNn());
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }


    @GetMapping
    public List<RetUser> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{userId}")
    public RetUser getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/delete")
    public boolean removeUser(int userId) {
        return userService.removeUser(userId);
    }

    @PostMapping("/update")
    public Map<String, String> modifyUser(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        boolean result = userService.modifyUser(user);
        if(result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @PostMapping("/emailCheck")
    public boolean emailCheck(String email) {
        return userService.getEmail(email);
    }

    @PostMapping("/nnCheck")
    public boolean nnCheck(String nickname) {
        return userService.getNN(nickname);
    }
}
