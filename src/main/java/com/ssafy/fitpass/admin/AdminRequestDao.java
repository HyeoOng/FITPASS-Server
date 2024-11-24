package com.ssafy.fitpass.admin;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminRequestDao {
    /**
     * 관리자에게 요청을 보내는 메서드입니다.
     *
     * @param adminRequest
     * @return 등록된 행 수 (등록 성공 : 1)
     */
    int insertRequest(AdminRequest adminRequest);

    /**
     * 모든 관리자 요청을 가져오는 메서드입니ㅏㄷ.
     *
     * @return 모든 관리자 요청
     */
    List<AdminRequest> selectAllRequests();

    /**
     * 요청을 삭제하는 메서드입니다.
     *
     * @param requestId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    int deleteRequest(int requestId);
}
