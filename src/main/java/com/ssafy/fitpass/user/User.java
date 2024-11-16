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

    // getter와 setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
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
