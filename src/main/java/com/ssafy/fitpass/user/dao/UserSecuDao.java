package com.ssafy.fitpass.user.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserSecuDao {
    /***
     * 사용자 ID와 암호화에 사용되는 Salt를 등록하는 메서드입니다.
     * @param userId와 salt값을 Map으로 묶어 전달
     */
    public void insertInfo(Map<String, String> saltMap);

    /***
     * userId의 salt값을 반환하는 메서드입니다.
     * @param userEmail
     * @return salt 값을 반환
     */
    public String selectSalt(String userEmail);
}
