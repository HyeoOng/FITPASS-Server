package com.ssafy.fitpass.auth;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@CrossOrigin("http://localhost:5173")
public class EmailVerificationController {

    private final MailService mailService;
    private final VerificationCodeService codeService;

    public EmailVerificationController(MailService mailService, VerificationCodeService codeService) {
        this.mailService = mailService;
        this.codeService = codeService;
    }

    /**
     * 이메일로 인증 코드를 전송하는 API입니다.
     *
     * @param request (email)
     * @return map
     */
    @PostMapping("/send-code")
    public Map<String, String> sendCode(@RequestBody Map<String, String> request) {
        Map<String, String> map = new HashMap<>();
        String email = request.get("email");
        try {
            String code = codeService.generateCode(email);
            System.out.println("verification code: " + code);
            mailService.sendVerificationCode(email, code);
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("msg", "fail");
        }
        return map;
    }

    /**
     * 이메일과 인증 코드를 검증하는 API입니다.
     *
     * @param request (email, code)
     * @return map
     */
    @PostMapping("/verify-code")
    public Map<String, String> verifyCode(@RequestBody Map<String, String> request) {
        Map<String, String> map = new HashMap<>();

        String email = request.get("email");
        String code = request.get("code");
        boolean isValid = codeService.verifyCode(email, code);

        if (isValid) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }
}

