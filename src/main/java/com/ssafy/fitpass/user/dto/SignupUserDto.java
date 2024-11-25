package com.ssafy.fitpass.user.dto;

import com.ssafy.fitpass.exception.InputException;

public class SignupUserDto {
    private String name;
    private String email;
    private String password;
    private String nn;
    private String profile;

    public SignupUserDto() {
    }

    public SignupUserDto(String name, String email, String password, String nn, int admin, String profile) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setNn(nn);
        setProfile(profile);
    }

    // Getter와 Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InputException("이름은 공백이 될 수 없습니다.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InputException("올바른 이메일 형식을 입력하세요.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new InputException("비밀번호는 null일 수 없습니다.");
        }
        if (password.length() < 5) {
            throw new InputException("비밀번호는 최소 5자리 이상이어야 합니다.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InputException("비밀번호는 최소 하나의 대문자를 포함해야 합니다.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InputException("비밀번호는 최소 하나의 소문자를 포함해야 합니다.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InputException("비밀번호는 최소 하나의 숫자를 포함해야 합니다.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?:].*")) {
            throw new InputException("비밀번호는 최소 하나의 특수 문자를 포함해야 합니다.");
        }
        this.password = password;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        if (nn == null || nn.trim().isEmpty()) {
            throw new InputException("닉네임은 공백이 될 수 없습니다.");
        }
        if (nn.length() < 2) {
            throw new InputException("닉네임은 최소 2글자 이상이어야 합니다.");
        }
        this.nn = nn;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nn + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
