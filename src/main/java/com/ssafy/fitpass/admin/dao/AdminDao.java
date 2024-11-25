package com.ssafy.fitpass.admin.dao;

import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 모든 관리자를 조회하는 메서드입니다.
     *
     * @return 관리자 정보를 반환
     */
    List<User> selectAllAdmin();

    /**
     * 관리자 권한을 추가하는 메서드입니다.
     *
     * @param userId (유저 아이디)
     * @return 수정된 행 수 (정상 수정 : 1)
     */
    int createAdmin(int userId);

    /**
     * 관리자 권한을 삭제하는 메서드입니다.
     *
     * @param userId (유저 아이디)
     * @return 수정된 행 수 (정상 수정 : 1)
     */
    int deleteAdmin(int userId);
}
