package com.ssafy.fitpass.user.dao;

import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.dto.SignupUserDto;
import com.ssafy.fitpass.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    /**
     * 회원 가입시 사용되는 메서드입니다.
     *
     * @param user
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    int insertUser(User user);

    /**
     * 로그인시 사용되는 메서드입니다.
     *
     * @param map (이메일, 비밀번호 필요)
     * @return 회원
     */
    RetUser login(Map<String, String> map);

    int selectUserByEmail(String email);

    /**
     * 회원 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return userId에 해당하는 회원
     */
    RetUser selectOne(int userId);

    /**
     * 모든 회원 정보를 조회하는 메서드입니다.
     *
     * @return 모든 회원
     */
    List<RetUser> selectAll();

    /**
     * 회원 정보를 수정하는 메서드입니다.
     *
     * @param user
     * @return 수정된 행 수 (수정 성공 : 1)
     */
    int updateUser(User user);

    /**
     * 회원을 삭제하는 메서드입니다.
     *
     * @param userId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    int deleteUser(int userId);

    /**
     * 이메일 중복을 확인하는 메서드입니다.
     *
     * @param email
     * @return email에 해당하는 이메일 개수
     */
    int checkEmail(String email);

    /**
     * 닉네임 중복을 확인하는 메서드입니다.
     *
     * @param nickname
     * @return nickname에 해당하는 닉네임 개수
     */
    int checkNn(String nickname);

    /**
     * 닉네임에 해당하는 유저를 반환하는 메서드입니다.
     *
     * @param nn
     * @return 닉네임에 관련된 모든 유저
     */
    List<RetUser> selectAllByNn(String nn);

    int selectUserId(String nn);
}
