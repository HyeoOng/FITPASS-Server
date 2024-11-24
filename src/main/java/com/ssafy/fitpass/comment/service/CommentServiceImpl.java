package com.ssafy.fitpass.comment.service;

import com.ssafy.fitpass.comment.dto.RetCommentDto;
import com.ssafy.fitpass.comment.dao.CommentDao;
import com.ssafy.fitpass.comment.dto.PostCommentDto;
import com.ssafy.fitpass.comment.dto.PutCommentDto;
import com.ssafy.fitpass.comment.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public boolean createComment(PostCommentDto postCommentDto) {
        // DTO -> entity 변환
        Comment comment = new Comment();
        comment.setUserId(postCommentDto.getUserId());
        comment.setPostId(postCommentDto.getPostId());
        comment.setComment(postCommentDto.getComment());

        return commentDao.insertComment(comment) == 1;
    }

    @Override
    public List<RetCommentDto> getCommentsByPost(int postId) {
        // DAO에서 엔티티 리스트 반환
        List<Comment> comments = commentDao.selectAll(postId);

        // 엔티티 -> DTO 변환
        return comments.stream().map(comment -> {
            RetCommentDto dto = new RetCommentDto();
            dto.setCommentId(comment.getCommentId());
            dto.setUserId(comment.getUserId());
            dto.setPostId(comment.getPostId());
            dto.setComment(comment.getComment());
            dto.setCreateAt(comment.getCreateAt());
            dto.setUpdateAt(comment.getUpdateAt());
            return dto;
        }).toList();
    }

    @Override
    public boolean removeComment(int commentId) {
        return commentDao.deleteComment(commentId) == 1;
    }

    @Override
    public boolean modifyComment(PutCommentDto putCommentDto) {
        // DTO -> entity 변환
        Comment comment = new Comment();
        comment.setUserId(putCommentDto.getUserId());
        comment.setPostId(putCommentDto.getPostId());
        comment.setComment(putCommentDto.getComment());
        return commentDao.updateComment(comment) == 1;
    }
}
