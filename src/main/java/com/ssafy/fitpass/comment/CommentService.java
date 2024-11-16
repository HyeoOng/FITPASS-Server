package com.ssafy.fitpass.comment;

import java.util.List;

public interface CommentService {
    boolean createComment(Comment comment);
    List<Comment> getCommentsByPost(int postId);
    boolean removeComment(int commentId);
    boolean modifyComment(Comment comment);
}
