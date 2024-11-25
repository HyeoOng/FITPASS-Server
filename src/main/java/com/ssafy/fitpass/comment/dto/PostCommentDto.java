package com.ssafy.fitpass.comment.dto;

public class PostCommentDto {
    private int userId;
    private int postId;
    private String comment;

    public PostCommentDto() {
    }

    public PostCommentDto(int postId, String comment) {
        setPostId(postId);
        setComment(comment);
    }

    public PostCommentDto(int userId, int postId, String comment) {
        setUserId(userId);
        setPostId(postId);
        setComment(comment);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("userId는 1 이상의 값이어야 합니다.");
        }
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("postId는 1 이상의 값이어야 합니다.");
        }
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 비워둘 수 없습니다.");
        }
        this.comment = comment.trim();
    }

    @Override
    public String toString() {
        return "PostCommentDto{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", comment='" + comment + '\'' +
                '}';
    }
}