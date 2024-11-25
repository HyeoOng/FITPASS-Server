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
                map.put("msg", "이메일 인증이 완료되지 않았습니다. 인증 후 다시 시도해 주세요.");
                return map;
            }
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
                    } catch (RegFDException e){
                        map.put("msg", "fail to save profile"); // SAL0001
                        return map;
                    }
                    map.put("msg", "success");
                } else {
                    map.put("msg", "fail to save profile at db"); //DAL0001
                }
            } else { // 로그인 정보가 존재하지 않는 경우
                map.put("msg", "fail"); // UAL0001
            }
        } catch (InputException e) {
            map.put("msg", e.getMessage()); // UAL0003
        } catch (Exception e) {
            map.put("msg", e.getMessage());  // SAL0002
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

            map.put("msg", "success");
            map.put("userId", loginedUser.getUserId());
            map.put("nickname", loginedUser.getNn());
            map.put("admin", loginedUser.getAdmin());
        } catch (UserException e) {
            map.put("msg", e.getMessage());
            if (e.getErrorCode().equals("TC")) {
                map.put("remainingAttempts", loginAttemptService.getMaxAttempts() - loginAttemptService.getAttempts(user.getEmail())); // UAL0002
            }else if(e.getErrorCode().equals("NF")) {
                // UAL0001
            }
        } catch (Exception e) {
            map.put("msg", "로그인 중 오류가 발생했습니다."); // SAL0002
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
            map.put("msg", "fail"); // SAL0002
        }
        return map;
    }


    @GetMapping
    public List<RetUser> getAllUsers() {
        try{
            return userService.getAllUsers();
        } catch (RegDBException e){
            // DAL0001
        } catch (Exception e) {
            // SAL0002
        }
        return null;
    }


    @GetMapping("/{userId}")
    public RetUser getUser(@PathVariable int userId) {
        try{
            return userService.getUser(userId);
        }catch (RegDBException e){
            // DAL0001
        } catch (Exception e) {
            // SAL0002
        }
        return null;
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
                // DAL
            }
        }catch (RegDBException e){
            map.put("flag", false);
            map.put("msg", e.getMessage());
            // DAL0001
        } catch (Exception e) {
            // SAL0002
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String, String> modifyUser(@RequestBody PutUserDto user) {
        Map<String, String> map = new HashMap<>();
        try{
            boolean result = userService.modifyUser(user);
            if(result){
                map.put("msg", "success");
            }else{
                // DAL0001
            }
        }catch (RegDBException e){
            // DAL0001
        } catch (Exception e) {
            // SAL0002
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
            map.put("msg", "fail1"); // UAL0001
        } else { // 아닐 경우
            try{
                boolean isDuplicate = userService.getEmail(email); // 이메일 중복 체크
                if(isDuplicate) { // 중복인 경우
                    map.put("msg", "fail2");
                } else { // 아닐 경우
                    map.put("msg", "success");
                }
            }catch (RegDBException e){
                // DAL0001
            } catch (Exception e) {
                // SAL0002
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
            map.put("msg", "fail1"); // UAL0001
        } else { // 값이 제대로 존재할 경우
            try{
                boolean isDuplicate = userService.getNN(nickname); // 중복된 닉네임인지 확인
                if(isDuplicate) { // 중복일 경우
                    map.put("msg", "fail2"); // UAL0004
                } else { // 아닐 경우
                    map.put("msg", "success");
                }
            } catch (RegDBException e){
                // DAL0001
            } catch (Exception e) {
//                // SAL0002
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
