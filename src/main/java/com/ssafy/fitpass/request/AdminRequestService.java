package com.ssafy.fitpass.request;

public interface AdminRequestService {
    boolean createRequest(AdminRequest adminRequest);
    boolean removeRequest(int requestId);
}
