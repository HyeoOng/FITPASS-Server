package com.ssafy.fitpass.user.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("application")
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5; // 최대 허용 시도 횟수
    private final ConcurrentHashMap<String, Integer> loginAttempts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> blockTimes = new ConcurrentHashMap<>();
    private static final long BLOCK_TIME_DURATION = 5 * 60 * 1000; // 차단 시간 (5분)

    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    // 로그인 시도 횟수 증가
    public void increaseAttempts(String email) {
        loginAttempts.merge(email, 1, Integer::sum); // 기존 값에 1 증가
        // 차단 시간 체크
        if (getAttempts(email) >= MAX_ATTEMPTS) {
            blockTimes.put(email, System.currentTimeMillis());
        }
    }

    // 로그인 시도 횟수 초기화
    public void resetAttempts(String email) {
        loginAttempts.remove(email);
        blockTimes.remove(email);
    }

    // 현재 시도 횟수 가져오기
    public int getAttempts(String email) {
        return loginAttempts.getOrDefault(email, 0);
    }

    // 사용자 차단 여부 확인
    public boolean isBlocked(String email) {
        if (getAttempts(email) < MAX_ATTEMPTS) {
            return false;
        }
        // 차단 시간이 지나면 차단 해제
        Long blockTime = blockTimes.get(email);
        if (blockTime != null && System.currentTimeMillis() - blockTime > BLOCK_TIME_DURATION) {
            resetAttempts(email);
            return false;
        }
        return true;
    }
}
