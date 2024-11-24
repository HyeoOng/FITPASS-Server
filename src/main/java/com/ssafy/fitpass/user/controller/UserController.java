package com.ssafy.fitpass.user.controller;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoService;
import com.ssafy.fitpass.user.service.LoginAttemptService;
import com.ssafy.fitpass.user.dto.LoginUserDto;
import com.ssafy.fitpass.user.dto.PutUserDto;
import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.dto.SignupUserDto;
import com.ssafy.fitpass.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final PhotoService photoService;
    private final LoginAttemptService loginAttemptService;

    public UserController(UserService userService, PhotoService photoService, LoginAttemptService loginAttemptService) {
        this.userService = userService;
        this.photoService = photoService;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/signup")
//    public Map<String, Object> signup(@RequestBody SignupUserDto user) {
    public Map<String, Object> signup(
            @RequestPart("user") SignupUserDto user,
            @RequestPart("file") MultipartFile file) {

        System.out.println("data 잘 받았는지 확인해보자-------------------");
        System.out.println(user);
        System.out.println(file.getOriginalFilename());
        System.out.println("------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        try {
            boolean result = userService.signup(user);
            if (result) {

                int userId = userService.getUserId(user.getNn());
                Photo photo = new Photo();

                if(!file.isEmpty()){
                    photo.setFile(file);
                } else {
                    map.put("msg", "fail to store profile");
                    return map;
                }

                String storeName = photoService.generateStoreFileName(photo.getUploadFileName());
                String saveFolder = "/profile/" + userId + "/" + storeName;
                photo.setStoreFileName(storeName);
                photo.setSaveFolder(saveFolder);

                if(userService.createProfile(userId, photo)){
                    try{
                        photoService.saveFile(file, userId, storeName, "profile/");
                    } catch (Exception e){
                        map.put("msg", "fail to save profile");
                        return map;
                    }
                    map.put("msg", "success");
                } else {
                    map.put("msg", "fail to save profile at db");
                }
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

//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody LoginUserDto user, HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        String email = user.getEmail();
//
//        // 1. 이메일 존재 여부 확인
//        if (!userService.isUserExist(email)) {
//            map.put("msg", "존재하지 않는 이메일입니다.");
//            return map;
//        }
//
//        if (loginAttemptService.isBlocked(email)) {
//            map.put("msg", "로그인 시도 횟수 초과로 인해 차단되었습니다.");
//            return map;
//        }
//
//        RetUser loginedUser = userService.login(user);
//        if (loginedUser != null) {
//            request.getSession().setAttribute("user", loginedUser);
//            map.put("msg", "success");
//            map.put("userId", loginedUser.getUserId());
//            map.put("nickname", loginedUser.getNn());
//            loginAttemptService.resetAttempts(email); // 로그인 성공 시 시도 횟수 초기화
//        } else {
//            System.out.println("check:" + email);
//            loginAttemptService.increaseAttempts(email);
//            map.put("msg", "fail");
//            map.put("remainingAttempts",
//                    loginAttemptService.getMaxAttempts() - loginAttemptService.getAttempts(email));
//        }
//        return map;
//    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginUserDto user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            System.out.println("로그인 시도한 정보: " + user);
            RetUser loginedUser = userService.handleLogin(user);
            System.out.println("로그인 결과 정보: " + loginedUser);
            // 로그인 성공 시 세션에 사용자 정보 저장
            request.getSession().setAttribute("user", loginedUser);

            map.put("msg", "success");
            map.put("userId", loginedUser.getUserId());
            map.put("nickname", loginedUser.getNn());
        } catch (IllegalArgumentException e) {
            map.put("msg", e.getMessage());
            if (e.getMessage().equals("잘못된 이메일 또는 비밀번호입니다.")) {
                map.put("remainingAttempts", loginAttemptService.getMaxAttempts() - loginAttemptService.getAttempts(user.getEmail()));
            }
        } catch (Exception e) {
            map.put("msg", "로그인 중 오류가 발생했습니다.");
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
    public Map<String, String> modifyUser(@RequestBody PutUserDto user) {
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

    @GetMapping("/curr")
    public Map<String, Object> getCurrentUser(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);

        if(session != null) {
            map.put("user", session.getAttribute("user"));
            map.put("login", true);
        }else{
            map.put("login", false);
        }

        return map;
    }
}
