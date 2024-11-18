package com.ssafy.fitpass.user;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
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
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> map = new HashMap<>();
        RetUser loginedUser = userService.login(user);
        if (loginedUser != null) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping("/logout")
    public void logout() {

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
