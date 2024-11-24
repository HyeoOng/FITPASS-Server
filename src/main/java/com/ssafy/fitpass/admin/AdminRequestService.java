package com.ssafy.fitpass.admin;

import java.util.List;

public interface AdminRequestService {
    boolean createRequest(AdminRequest adminRequest);
    List<AdminRequest> getAllRequests();
    boolean removeRequest(int requestId);
}
