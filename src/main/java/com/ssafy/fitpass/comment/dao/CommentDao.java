package com.ssafy.fitpass.comment.dao;

import com.ssafy.fitpass.comment.dto.RetCommentDto;
import com.ssafy.fitpass.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    /***
     * 댓글을 등록하는 메서드입니다.
     * @param comment
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    public int insertComment(Comment comment);

    /***
     * 한 게시글(postId)의 모든 댓글을 조회하는 메서드입니다.
     * @return postId 글에 작성된 댓글 목록 반환
     */
    public List<Comment> selectAll(int postId);

    /***
     * 댓글을 삭제하는 메서드입니다.
     * @param commentId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    public int deleteComment(int commentId);

    /***
     * 댓글을 수정하는 메서드입니다.
     * @param comment
     * @return 수정된 행 수 (수정 성공 : 1)
     */
    public int updateComment(Comment comment);
}
