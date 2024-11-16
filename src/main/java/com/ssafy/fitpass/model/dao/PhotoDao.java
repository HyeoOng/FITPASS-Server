package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.Photo;
import org.apache.ibatis.annotations.Param;

public interface PhotoDao {
    /***
     * PostPhoto 테이블에 photo 정보를 등록하는 메서드입니다.
     * @param photo
     * @return 등록 성공 여부 (등록 성공 : 1)
     */
    int insertPostPhoto(Photo photo);

    /***
     * Profile 테이블에 프로필 이미지를 등록하는 메서드입니다.
     * @param userId
     * @param photo
     * @return 등록 성공 여부 (등록 성공 : 1)
     */
    int insertProfile(@Param("userId") int userId, @Param("photo")Photo photo);

    /***
     * 등록된 게시글의 이미지 파일을 변경하는 메서드입니다.
     * @param photo
     * @return 등록 성공 여부 (등록 성공 : 1)
     */
    int updatePostPhoto(Photo photo);

    /***
     * 사용자의 프로필 이미지를 변경하는 메서드입니다.
     * @param userId
     * @param photo
     * @return
     */
    int updateProfile(@Param("userId") int userId, @Param("photo")Photo photo);
}
