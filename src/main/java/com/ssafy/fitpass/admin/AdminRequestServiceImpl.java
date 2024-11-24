package com.ssafy.fitpass.admin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRequestServiceImpl implements AdminRequestService {

    private final AdminRequestDao adminRequestDao;

    public AdminRequestServiceImpl(AdminRequestDao adminRequestDao) {
        this.adminRequestDao = adminRequestDao;
    }

    @Override
    public boolean createRequest(AdminRequest adminRequest) {
        return adminRequestDao.insertRequest(adminRequest) == 1;
    }

    @Override
    public List<AdminRequest> getAllRequests() {
        return adminRequestDao.selectAllRequests();
    }

    @Override
    public boolean removeRequest(int requestId) {
        return adminRequestDao.deleteRequest(requestId) == 1;
    }
}
