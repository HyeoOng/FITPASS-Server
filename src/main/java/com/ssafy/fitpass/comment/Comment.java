package com.ssafy.fitpass.comment;

import java.util.Date;

public class Comment {
    private int commentId, userId, postId;
    private String comment;
    private Date createAt, updateAt;

    // 기본 생성자
    public Comment(){}
    // DB에서 등록된 댓글 조회 시 사용
    public Comment(int commentId, int userId, int postId, String comment) {

    }
    // DB에 등록 시 사용
    public Comment(int userId, int postId, String comment) {

    }

    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        if(!comment.isEmpty()){
            this.comment = comment;
        }
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
