package com.ssafy.fitpass.admin.service;

import com.ssafy.fitpass.admin.dto.PostAdminRequestDto;
import com.ssafy.fitpass.admin.dto.RetAdminRequestDto;
import com.ssafy.fitpass.admin.dao.AdminRequestDao;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.dao.DataAccessException;

@Service
public class AdminRequestServiceImpl implements AdminRequestService {

    private final AdminRequestDao adminRequestDao;

    public AdminRequestServiceImpl(AdminRequestDao adminRequestDao) {
        this.adminRequestDao = adminRequestDao;
    }

    @Override
    public boolean createRequest(PostAdminRequestDto adminRequest) {
        try {
            return adminRequestDao.insertRequest(adminRequest) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 생성 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 생성 중 예상치 못한 오류가 발생했습니다.");
        }
    }

    @Override
    public List<RetAdminRequestDto> getAllRequests() {
        try {
            return adminRequestDao.selectAllRequests();
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 목록 조회 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 목록 조회 중 예상치 못한 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean removeRequest(int requestId) {
        try {
            return adminRequestDao.deleteRequest(requestId) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 삭제 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 삭제 중 예상치 못한 오류가 발생했습니다.");
        }
    }
}
