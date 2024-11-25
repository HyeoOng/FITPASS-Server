package com.ssafy.fitpass.admin.entity;

import java.util.Date;

public class AdminRequest {
    private int reqId, userId;
    private String title, content, nn;
    private Date created_at;

    public AdminRequest() {
    }

    public AdminRequest(int reqId, int userId, String title, String content, String nn, Date created_at) {
        this.reqId = reqId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.nn = nn;
        this.created_at = created_at;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "AdminRequest{" +
                "reqId=" + reqId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
