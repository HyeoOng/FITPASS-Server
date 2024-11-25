package com.ssafy.fitpass.user.controller;

import com.ssafy.fitpass.auth.MailService;
import com.ssafy.fitpass.auth.VerificationCodeService;
import com.ssafy.fitpass.exception.InputException;
import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.exception.RegFDException;
import com.ssafy.fitpass.exception.UserException;
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
    private final VerificationCodeService verificationCodeService;

    public UserController(UserService userService, PhotoService photoService, LoginAttemptService loginAttemptService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.photoService = photoService;
        this.loginAttemptService = loginAttemptService;
        this.verificationCodeService = verificationCodeService;
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

            if (!verificationCodeService.isEmailVerified(user.getEmail())) {
                map.put("code", "UAL0008");
                return map;
            }
            boolean result = userService.signup(user);
            if (result) {

                int userId = userService.getUserId(user.getNn());
                Photo photo = new Photo();

                if(!file.isEmpty()){
                    photo.setFile(file);
                } else {
                    map.put("flag", false);
                    map.put("code", "UAL0007"); // 파일 데이터를 입력하지 않음
                    return map;
                }

                String storeName = photoService.generateStoreFileName(photo.getUploadFileName());
                String saveFolder = "/profile/" + userId + "/" + storeName;
                photo.setStoreFileName(storeName);
                photo.setSaveFolder(saveFolder);

                if(userService.createProfile(userId, photo)){
                    try{
                        photoService.saveFile(file, userId, storeName, "profile/");
                    } catch (RegFDException e){
                        map.put("code", "SAL0001"); // SAL0001
                        map.put("flag", false);
                        return map;
                    }
                    map.put("flag", true);
                } else {
                    map.put("code", "DAL0001"); //DAL0001
                    map.put("flag", false);
                }
            } else { // 로그인 정보가 존재하지 않는 경우
                map.put("code", "UAL0001"); // UAL0001
                map.put("flag", false);
            }
        } catch (RegDBException e) {
            map.put("code", "DAL0001"); // DAL0001
            map.put("flag", false);
        } catch (Exception e) {
            map.put("code", "SAL0002");  // SAL0002
            map.put("flag", false);
        }
        return map;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginUserDto user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        try {
            RetUser loginedUser = userService.handleLogin(user);
            // 로그인 성공 시 세션에 사용자 정보 저장
            request.getSession().setAttribute("user", loginedUser);

            map.put("flag", true);

            map.put("userId", loginedUser.getUserId());
            map.put("nickname", loginedUser.getNn());
            map.put("admin", loginedUser.getAdmin());
        } catch (UserException e) {
            map.put("msg", e.getMessage());
            if (e.getErrorCode().equals("TC")) {
                map.put("code", "UAL0002"); // UAL0002
                map.put("flag", false);
            }else if(e.getErrorCode().equals("NF")) {
                map.put("code", "UAL0001"); // UAL0001
                map.put("flag", false);
            }
        } catch (Exception e) {
            map.put("msg", "SAL0002"); // SAL0002
            map.put("flag", false);
        }

        return map;
    }


    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            map.put("flag", true);
        } else {
            map.put("code", "SAL0002"); // SAL0002
            map.put("flag", false);
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
    public Map<String, Object> removeUser(int userId) {
        Map<String, Object> map = new HashMap<>();
        try{
            boolean res =  userService.removeUser(userId);
            if(res){
                map.put("flag", true);
            }else{
                map.put("flag", false);
                map.put("code", "DAL0001"); // DAL
            }
        }catch (RegDBException e){
            map.put("flag", false);
            map.put("code", "DAL0001"); // DAL0001
        } catch (Exception e) {
            map.put("flag", false);
            map.put("code", "SAL0002"); // SAL0002
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String, Object> modifyUser(@RequestBody PutUserDto user) {
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = userService.modifyUser(user);
            if(result){
                map.put("flag", true);
            }else{
                map.put("flag", false);
                map.put("code", "DAL0001"); // DAL0001
            }
        }catch (RegDBException e){
            map.put("flag", false);
            map.put("code", "DAL0001"); // DAL0001
        } catch (Exception e) {
            map.put("flag", false);
            map.put("code", "SAL0002"); // SAL0002
        }
        return map;
    }

    @PostMapping("/emailCheck")
    public Map<String, Object> emailCheck(@RequestBody Map<String, String> requestData) {
        Map<String, Object> map = new HashMap<>();
        // 프론트에서 받아온 json 형식에서 email 값 꺼내기
        String email = requestData.get("email");
        // 이메일 값이 null이거나 공백일 경우
        if (email == null || email.isBlank()) {
            map.put("code", "UAL0001"); // UAL0001
        } else { // 아닐 경우
            try{
                boolean isDuplicate = userService.getEmail(email); // 이메일 중복 체크
                if(isDuplicate) { // 중복인 경우
                    map.put("code", "UAL0004");
                } else { // 아닐 경우
                    map.put("msg", "success");
                }
            }catch (RegDBException e){
                map.put("flag", false);
                map.put("code", "DAL0001"); // DAL0001
            } catch (Exception e) {
                map.put("flag", false);
                map.put("code", "SAL0002"); // SAL0002
            }
        }
        return map;
    }

    @PostMapping("/nnCheck")
    public Map<String, Object> nnCheck(@RequestBody Map<String, String> requestData) {
        Map<String, Object> map = new HashMap<>();
        // 프론트에서 받아온 json 형식에서 nn 값 꺼내기
        String nickname = requestData.get("nn");
        // nn이 null이거나 공백이라면,
        if (nickname == null || nickname.isBlank()) {
            map.put("code", "UAL0001"); // UAL0001
            map.put("flag", false);
        } else { // 값이 제대로 존재할 경우
            try{
                boolean isDuplicate = userService.getNN(nickname); // 중복된 닉네임인지 확인
                if(isDuplicate) { // 중복일 경우
                    map.put("code", "UAL0004"); // UAL0001
                    map.put("flag", false);
                } else { // 아닐 경우
                    map.put("flag", true);
                }
            } catch (RegDBException e){
                map.put("flag", false);
                map.put("code", "DAL0001"); // DAL0001
            } catch (Exception e) {
                map.put("flag", false);
                map.put("code", "SAL0002"); // SAL0002
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
            map.put("user", null);
        }

        return map;
    }
}
