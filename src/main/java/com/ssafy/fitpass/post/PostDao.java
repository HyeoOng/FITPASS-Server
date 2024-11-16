package com.ssafy.fitpass.post;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    /**
     * 글을 등록하는 메서드입니다.
     *
     * @param post
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    int insertPost(Post post);

    /**
     * 글을 조회하는 메서드입니다.
     *
     * @param postId
     * @return postId에 해당하는 글
     */
    Post selectOne(int postId);

    /**
     * 전체 공개인 모든 글을 조회하는 메서드입니다.
     *
     * @return 전체 공개인 모든 글
     */
    List<Post> selectAll();

    /**
     * 글을 수정하는 메서드입니다.
     *
     * @param post
     * @return 수정된 행 수 (수정 성공 : 1)
     */
    int updatePost(Post post);

    /**
     * 글을 삭제하는 메서드입니다.
     *
     * @param postId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    int deletePost(int postId);

    /**
     * 친구 공개인 모든 글을 조회하는 메서드입니다.
     *
     * @param userId
     * @return 친구 공개인 모든 글
     */
    List<Post> selectFriendPosts(int userId);

    /***
     * userId 사용자가 작성한 글(전체 공개)을 모두 조회하는 메서드입니다.
     * @param userId
     * @return
     */
    List<Post> selectUserPost(int userId);
}
