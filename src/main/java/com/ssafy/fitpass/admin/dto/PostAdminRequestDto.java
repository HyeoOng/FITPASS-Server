package com.ssafy.fitpass.admin.dto;

import com.ssafy.fitpass.exception.InputException;

public class PostAdminRequestDto {
    private int userId;
    private String title, content;

    public PostAdminRequestDto() {
    }

    public PostAdminRequestDto(int userId, String title, String content) {
        setUserId(userId);
        setTitle(title);
        setContent(content);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new InputException("User ID는 0보다 커야 합니다.");
        }
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InputException("Title은 비어있을 수 없습니다.");
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new InputException("Content는 비어있을 수 없습니다.");
        }
        this.content = content;
    }

    @Override
    public String toString() {
        return "AdminRequest{" +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
