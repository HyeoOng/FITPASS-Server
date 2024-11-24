package com.ssafy.fitpass.admin;

public class AdminRequest {
    private int reqId, userId;
    private String title, content;

    public AdminRequest() {
    }

    public AdminRequest(int userId, String title, String content) {
        setUserId(userId);
        setTitle(title);
        setContent(content);
    }

    public AdminRequest(int reqId, int userId, String title, String content) {
        setReqId(reqId);
        setUserId(userId);
        setTitle(title);
        setContent(content);
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int rqId) {
        this.reqId = rqId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID는 0보다 커야 합니다.");
        }
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title은 비어있을 수 없습니다.");
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content는 비어있을 수 없습니다.");
        }
        this.content = content;
    }

    @Override
    public String toString() {
        return "AdminRequest{" +
                "reqId=" + reqId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
