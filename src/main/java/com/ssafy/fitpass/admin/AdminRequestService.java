package com.ssafy.fitpass.admin;

public interface AdminRequestService {
    boolean createRequest(AdminRequest adminRequest);
    boolean removeRequest(int requestId);
}
