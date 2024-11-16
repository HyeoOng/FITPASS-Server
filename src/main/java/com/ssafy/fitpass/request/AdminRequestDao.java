package com.ssafy.fitpass.request;

import org.apache.ibatis.annotations.Mapper;

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
     * 요청을 삭제하는 메서드입니다.
     *
     * @param requestId
     * @return 삭제된 행 수 (삭제 성공 : 1)
     */
    int deleteRequest(int requestId);
}
