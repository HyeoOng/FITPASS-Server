package com.ssafy.fitpass.admin.service;

import com.ssafy.fitpass.admin.dto.PostAdminRequestDto;
import com.ssafy.fitpass.admin.dto.RetAdminRequestDto;
import com.ssafy.fitpass.admin.dao.AdminRequestDao;
import com.ssafy.fitpass.admin.entity.AdminRequest;
import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.entity.User;
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
    public boolean createRequest(PostAdminRequestDto postAdminRequestDto) {
        try {
            AdminRequest adminRequest = new AdminRequest();
            adminRequest.setUserId(postAdminRequestDto.getUserId());
            adminRequest.setTitle(postAdminRequestDto.getTitle());
            adminRequest.setContent(postAdminRequestDto.getContent());
            return adminRequestDao.insertRequest(adminRequest) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 생성 중 데이터베이스 오류가 발생했습니다."); // DAL0001
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 생성 중 예상치 못한 오류가 발생했습니다."); // SAL0002
        }
    }

    @Override
    public List<RetAdminRequestDto> getAllRequests() {
        try {
            List<AdminRequest> adminRequests = adminRequestDao.selectAllRequests();
            return adminRequests.stream()
                    .map(this::convertToDto) // 각 AdminRequest를 RetAdminRequestDto로 변환
                    .toList();               // 변환 결과를 리스트로 반환
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 목록 조회 중 데이터베이스 오류가 발생했습니다."); // DAL0001
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 목록 조회 중 예상치 못한 오류가 발생했습니다."); // SAL0002
        }
    }

    @Override
    public boolean removeRequest(int requestId) {
        try {
            return adminRequestDao.deleteRequest(requestId) == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 요청 삭제 중 데이터베이스 오류가 발생했습니다."); // DAL0001
        } catch (Exception e) {
            throw new RuntimeException("관리자 요청 삭제 중 예상치 못한 오류가 발생했습니다."); // SAL0002
        }
    }

    private RetUser convertToDto(User user) {
        if (user == null) return null;

        return new RetUser(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getNn(),
                user.getRegDate(),
                user.getAdmin(),
                user.getProfile()
        );
    }

    private RetAdminRequestDto convertToDto(AdminRequest adminRequest) {
        if (adminRequest == null) return null;

        RetAdminRequestDto dto = new RetAdminRequestDto();
        dto.setReqId(adminRequest.getReqId());
        dto.setUserId(adminRequest.getUserId());
        dto.setTitle(adminRequest.getTitle());
        dto.setContent(adminRequest.getContent());
        dto.setNn(adminRequest.getNn());

        return dto;
    }
}
