package com.ssafy.fitpass.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", e.getMessage());
        return map;
    }

    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> handleIllegalStateException(IllegalStateException e) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", e.getMessage());
        return map;
    }

    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeException(RuntimeException e) {
        Map<String, String> map = new HashMap<>();
        map.put("msg", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        return map;
    }
}

