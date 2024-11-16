package com.ssafy.fitpass.comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public boolean createComment(Comment comment) {
        return commentDao.insertComment(comment) == 1;
    }

    @Override
    public List<Comment> getCommentsByPost(int postId) {
        return commentDao.selectAll(postId);
    }

    @Override
    public boolean removeComment(int commentId) {
        return commentDao.deleteComment(commentId) == 1;
    }

    @Override
    public boolean modifyComment(Comment comment) {
        return commentDao.updateComment(comment) == 1;
    }
}
