package com.ssafy.fitpass.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    // 이메일에 해당하는 인증 코드를 저장하는 Map 객체
    private final Map<String, String> verificationCodes = new HashMap<>();

    /**
     * 주어진 이메일에 대해 6자리 랜덤 인증 코드를 생성하여 반환하는 메서드입니다.
     *
     * @param email (사용자 입력 이메일)
     * @return 6자리 랜덤 코드
     */
    public String generateCode(String email) {
        // 6자리 랜덤 숫자 생성
        String code = String.valueOf(new Random().nextInt(899999) + 100000);

        // 생성된 인증 코드를 verificationCodes Map에 이메일과 함께 저장
        verificationCodes.put(email, code);

        // 일정 시간 후 코드 삭제 (5분 후)
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                verificationCodes.remove(email);
            }
        }, 5 * 60 * 1000);

        return code;
    }

    /**
     * 주어진 이메일과 인증 코드가 일치하는지 확인하는 메서드입니다.
     *
     * @param email (사용자 입력 이메일)
     * @param code (사용자가 입력한 인증 코드)
     * @return 같을 경우 true, 다를 경우 false
     */
    public boolean verifyCode(String email, String code) {
        // 저장된 인증 코드와 사용자가 입력한 인증 코드가 일치하는지 확인
        return code.equals(verificationCodes.get(email));
    }
}

