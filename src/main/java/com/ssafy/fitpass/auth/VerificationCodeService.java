package com.ssafy.fitpass.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    // 이메일에 해당하는 인증 코드를 저장하는 Map 객체
    private final Map<String, String> verificationCodes = new HashMap<>();
    // 인증이 완료된 이메일을 저장하는 Map 객체
    private final Map<String, Boolean> verifiedEmails = new HashMap<>();

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
     * 주어진 이메일과 인증 코드가 일치하는지 확인하고, 일치하면 이메일을 인증된 이메일 목록에 추가합니다.
     *
     * @param email (사용자 입력 이메일)
     * @param code (사용자가 입력한 인증 코드)
     * @return 인증이 성공하면 true, 실패하면 false
     */
    public boolean verifyCode(String email, String code) {
        // 인증 코드가 일치하면 인증된 이메일 목록에 추가
        if (code.equals(verificationCodes.get(email))) {
            verifiedEmails.put(email, true);  // 이메일 인증 완료 처리
            return true;
        }
        return false;
    }

    /**
     * 이메일이 인증된 상태인지 확인하는 메서드입니다.
     *
     * @param email (사용자 입력 이메일)
     * @return 이메일이 인증되었으면 true, 아니면 false
     */
    public boolean isEmailVerified(String email) {
        return verifiedEmails.getOrDefault(email, false);
    }
}
