package com.ssafy.fitpass.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/curr")
    public ResponseEntity<?> curr(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            RetUser retUser = (RetUser) session.getAttribute("user");
            response.put("login", true);
            response.put("user", retUser);
            return ResponseEntity.ok(response);
        }
        response.put("login", false);
        response.put("user", null);
        return ResponseEntity.ok(response);
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
    public Map<String, String> modifyUser(@RequestBody User user, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        int userId = ((RetUser) session.getAttribute("user")).getUserId();
        user.setUserId(userId);
        boolean result = userService.modifyUser(user);
        if(result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @PostMapping("/emailCheck")
    public Map<String, String> emailCheck(@RequestBody Map<String, String> requestData) {
        Map<String, String> map = new HashMap<>();
        // 프론트에서 받아온 json 형식에서 email 값 꺼내기
        String email = requestData.get("email");
        // 이메일 값이 null이거나 공백일 경우
        if (email == null || email.isBlank()) {
            map.put("msg", "fail1");
        } else { // 아닐 경우
            boolean isDuplicate = userService.getEmail(email); // 이메일 중복 체크
            if(isDuplicate) { // 중복인 경우
                map.put("msg", "fail2");
            } else { // 아닐 경우
                map.put("msg", "success");
            }
        }

        return map;
    }

    @PostMapping("/nnCheck")
    public Map<String, String> nnCheck(@RequestBody Map<String, String> requestData) {
        Map<String, String> map = new HashMap<>();
        // 프론트에서 받아온 json 형식에서 nn 값 꺼내기
        String nickname = requestData.get("nn");
        // nn이 null이거나 공백이라면,
        if (nickname == null || nickname.isBlank()) {
            map.put("msg", "fail1");
        } else { // 값이 제대로 존재할 경우
            boolean isDuplicate = userService.getNN(nickname); // 중복된 닉네임인지 확인
            if(isDuplicate) { // 중복일 경우
                map.put("msg", "fail2");
            } else { // 아닐 경우
                map.put("msg", "success");
            }
        }
        return map;
    }

    @GetMapping("/search")
    public List<RetUser> getUsersByNN(@RequestParam String nn) {
        return userService.getUserByNn(nn);
    }
}
