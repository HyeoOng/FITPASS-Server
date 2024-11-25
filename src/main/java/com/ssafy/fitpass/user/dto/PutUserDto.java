package com.ssafy.fitpass.user.dto;

import com.ssafy.fitpass.user.entity.User;

public class PutUserDto extends User {
    private int userId;
    private String email, name, nn;
    private int admin;
    private String profile;

    public PutUserDto() {
    }

    public PutUserDto(int userId, String email, String name, String nn, int admin, String profile) {
        setUserId(userId);
        setEmail(email);
        setName(name);
        setNn(nn);
        setAdmin(admin);
        setProfile(profile);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId는 0보다 큰 값이어야 합니다.");
        }
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$")) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        this.name = name;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        if (nn == null || nn.isBlank()) {
            throw new IllegalArgumentException("닉네임은 비어 있을 수 없습니다.");
        }
        this.nn = nn;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
//        if (admin != 0 && admin != 1) {
//            throw new IllegalArgumentException("admin 값은 0(일반 사용자) 또는 1(관리자)만 허용됩니다.");
//        }
        this.admin = admin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
//        if (profile != null && !profile.isBlank() && !profile.matches("^(https?://).+$")) {
//            throw new IllegalArgumentException("profile은 올바른 URL 형식이어야 합니다.");
//        }
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "PutUserDto{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", nn='" + nn + '\'' +
                ", admin=" + admin +
                ", profile='" + profile + '\'' +
                '}';
    }
}