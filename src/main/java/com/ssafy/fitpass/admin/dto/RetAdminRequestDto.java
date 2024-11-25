package com.ssafy.fitpass.admin.dto;

public class RetAdminRequestDto {
    private int reqId, userId;
    private String title, content, nn;

    public RetAdminRequestDto() {
    }

    public RetAdminRequestDto(int userId, String title, String content) {
        setUserId(userId);
        setTitle(title);
        setContent(content);
    }

    public RetAdminRequestDto(int reqId, int userId, String title, String content) {
        setReqId(reqId);
        setUserId(userId);
        setTitle(title);
        setContent(content);
    }

    public RetAdminRequestDto(int reqId, int userId, String title, String content, String nn) {
        setReqId(reqId);
        setUserId(userId);
        setTitle(title);
        setContent(content);
        setNn(nn);
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

    @Override
    public String toString() {
        return "AdminRequest{" +
                "reqId=" + reqId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", nn='" + nn + '\'' +
                '}';
    }
}
