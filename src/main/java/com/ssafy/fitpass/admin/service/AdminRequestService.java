package com.ssafy.fitpass.admin.service;

import com.ssafy.fitpass.admin.dto.PostAdminRequestDto;
import com.ssafy.fitpass.admin.dto.RetAdminRequestDto;

import java.util.List;

public interface AdminRequestService {
    boolean createRequest(PostAdminRequestDto adminRequest);
    List<RetAdminRequestDto> getAllRequests();
    boolean removeRequest(int requestId);
}
