package com.ssafy.fitpass.model.dto;

public class AdminRequest {
    private int rqId, userId;
    private String title, content;

    public AdminRequest() {
    }

    public AdminRequest(int userId, String title, String content) {
        setRqId(userId);
        setTitle(title);
        setContent(content);
    }

    public AdminRequest(int rqId, int userId, String title, String content) {
        setRqId(rqId);
        setRqId(userId);
        setTitle(title);
        setContent(content);
    }

    public int getRqId() {
        return rqId;
    }

    public void setRqId(int rqId) {
        this.rqId = rqId;
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

    @Override
    public String toString() {
        return "Request{" +
                "rqId=" + rqId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
