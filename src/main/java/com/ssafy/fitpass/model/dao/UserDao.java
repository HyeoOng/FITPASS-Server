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
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    int insertUser(User user);

    /**
     * 로그인시 사용되는 메서드입니다.
     *
     * @param map (이메일, 비밀번호 필요)
     * @return 회원
     */
    RetUser selectOne(Map<String, String> map);

    /**
     * 회원 정보를 조회하는 메서드입니다.
     *
     * @param userId
     * @return userId에 해당하는 회원
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
    int checkNickname(String nickname);

    /////////////////////Admin//////////////////

    /***
     * 모든 관리자를 조회하는 메서드입니다.
     *
     * @return 관리자 정보를 반환
     */
    List<User>  selectAllAdmin();

    /***
     * 관리자 권한을 추가하는 메서드입니다.
     * @param userId
     * @return 수정된 행 수 (정상 수정 : 1)
     */
    int updateAdmin(int userId);
}
