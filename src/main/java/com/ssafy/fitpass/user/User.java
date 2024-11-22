package com.ssafy.fitpass.user;

import java.util.Date;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String nn;
    private int admin;
    private Date regDate;
    private String profile;

    public User() {
    }

    public User(String name, String email, String password, String nn, int admin, Date regDate, String profile) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setNn(nn);
        setAdmin(admin);
        setRegDate(regDate);
        setProfile(profile);
    }

    public User(int userId, String name, String email, String password, String nn, int admin, Date regDate, String profile) {
        setUserId(userId);
        setName(name);
        setEmail(email);
        setPassword(password);
        setNn(nn);
        setAdmin(admin);
        setRegDate(regDate);
        setProfile(profile);
    }

    // Getter와 Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId는 0보다 커야 합니다.");
        }
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 공백이 될 수 없습니다.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("올바른 이메일 형식을 입력하세요.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("비밀번호는 최소 5자리 이상이어야 합니다.");
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println(password);
            throw new IllegalArgumentException("비밀번호는 최소 하나의 대문자를 포함해야 합니다.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 소문자를 포함해야 합니다.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 숫자를 포함해야 합니다.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?:].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나의 특수 문자를 포함해야 합니다.");
        }
        this.password = password;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        if (nn == null || nn.trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 공백이 될 수 없습니다.");
        }
        if (nn.length() < 2) {
            throw new IllegalArgumentException("닉네임은 최소 2글자 이상이어야 합니다.");
        }
        this.nn = nn;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
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
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nn + '\'' +
                ", admin=" + admin +
                ", regDate=" + regDate +
                ", profile='" + profile + '\'' +
                '}';
    }
}
