package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.Comment;

import java.util.List;

public interface CommentDao {

    /***
     * 댓글 등록
     * @param comment
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    public int insertComment(Comment comment);

    /***
     * 한 게시글(postId)의 모든 댓글 조회
     * @param postId
     * @return postId 글에 작성된 댓글 목록 반환
     */
    public List<Comment> selectAll(int postId);

    /***
     * 댓글 삭제
     * @param commentId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    public int deleteComment(int commentId);

    /***
     * 댓글 수정
     * @param comment
     * @return 수정된 행 수 (수정 성공 : 1)
     */
    public int updateComment(Comment comment);
}
