package com.ssafy.fitpass.user.dto;

import java.util.Date;

public class RetUser {
    public int userId;
    public String email, name, nn;
    public Date regDate;
    public int admin;
    public String profile;

    public RetUser() {}

    public RetUser(int userId, String email, String name, String nn, Date regDate, int admin, String profile) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nn = nn;
        this.regDate = regDate;
        this.admin = admin;
        this.profile = profile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "RetUser{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nn + '\'' +
                ", regDate=" + regDate +
                ", admin=" + admin +
                ", profile='" + profile + '\'' +
                '}';
    }
}

