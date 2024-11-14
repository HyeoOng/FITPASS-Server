package com.ssafy.fitpass.model.dao;

import com.ssafy.fitpass.model.dto.RetUser;
import com.ssafy.fitpass.model.dto.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    /**
     * 회원 가입시 사용되는 메서드입니다.
     *
     * @param user
     * @return 결과
     */
    int insertUser(User user);

    /**
     * 로그인시 사용되는 메서드입니다.
     *
     * @param map
     * @return 회원
     */
    RetUser selectOne(Map<String, String> map);

    /**
     * 회원 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return 회원
     */
    RetUser selectOne(String userId);

    /**
     * 모든 회원 정보를 조회하는 메서드입니다.
     *
     * @return 모든 회원
     */
    List<User> selectAll();

    /**
     * 회원 정보를 수정하는 메서드입니다.
     *
     * @param user
     * @return 결과
     */
    int updateUser(User user);

    /**
     * 회원을 삭제하는 메서드입니다.
     *
     * @param userId
     * @return 결과
     */
    int deleteUser(int userId);

    /**
     * 이메일 중복을 확인하는 메서드입니다.
     *
     * @param email
     * @return 해당 이메일 개수
     */
    int checkEmail(String email);

    /**
     * 닉네임 중복을 확인하는 메서드입니다.
     *
     * @param nickname
     * @return 해당 닉네임 개수
     */
    int checkNickname(String nickname);
}
