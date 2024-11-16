package com.ssafy.fitpass.user;

import java.util.Date;

public class RetUser {
    public int userId;
    public String email, name, nn;
    public Date regDate;
    public int admin;
    public String profile;

    public RetUser() {}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNn() {
        return nn;
    }

    public Date getRegDate() {
        return regDate;
    }

    public int getAdmin() {
        return admin;
    }

    public String getProfile() {
        return profile;
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

