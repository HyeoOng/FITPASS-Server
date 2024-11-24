package com.ssafy.fitpass.comment.service;

import com.ssafy.fitpass.comment.dto.RetCommentDto;
import com.ssafy.fitpass.comment.dto.PostCommentDto;
import com.ssafy.fitpass.comment.dto.PutCommentDto;

import java.util.List;

public interface CommentService {
    boolean createComment(PostCommentDto comment);
    List<RetCommentDto> getCommentsByPost(int postId);
    boolean removeComment(int commentId);
    boolean modifyComment(PutCommentDto comment);
}
