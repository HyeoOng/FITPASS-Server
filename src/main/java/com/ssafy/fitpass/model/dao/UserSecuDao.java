package com.ssafy.fitpass.model.dao;

import java.util.Map;

public interface UserSecuDao {
    /***
     * 사용자 ID와 암호화에 사용되는 Salt를 등록
     * @param userId와 salt값을 Map으로 묶어 전달
     */
    public void insertInfo(Map<String, String> saltMap);

    /***
     * userId의 salt값을 반환
     * @param userId
     * @return salt 값을 반환
     */
    public String selectSalt(int userId);
}