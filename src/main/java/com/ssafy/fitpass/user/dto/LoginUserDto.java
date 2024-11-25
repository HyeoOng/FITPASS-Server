package com.ssafy.fitpass.user.dto;

import com.ssafy.fitpass.exception.InputException;

public class LoginUserDto {
    private String email, password;

    public LoginUserDto(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InputException("이메일은 빈 값일 수 없습니다.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new InputException("비밀번호는 빈 값일 수 없습니다.");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password= " + password +
                '}';
    }
}