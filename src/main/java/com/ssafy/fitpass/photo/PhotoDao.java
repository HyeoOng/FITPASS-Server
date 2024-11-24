package com.ssafy.fitpass.photo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
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

    /***
     * 등록된 사진의 photoId를 가지고 오는 메서드입니다.
     * @param photo
     * @return photoId
     */
    int selectPhotoId(Photo photo);

    /***
     * 등록된 사용자의 프로필 사진의 ID를 가지고 오는 메서드입니다. (아직 mapper에 구현안헀는데 필요하면 구현하고 괄호 지워주세요..)
     * @param userId
     * @return
     */
    String selectPhotoIdbyUserId(int userId);
}
