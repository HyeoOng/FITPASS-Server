package com.ssafy.fitpass.admin.service;

import com.ssafy.fitpass.admin.dao.AdminDao;
import com.ssafy.fitpass.user.dto.RetUser;
import com.ssafy.fitpass.user.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;  // 데이터베이스와 상호작용하는 AdminDao 객체

    // AdminDao 객체를 주입받는 생성자
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    /**
     * 모든 관리자의 정보를 반환하는 메소드입니다.
     *
     * @return 모든 관리자를 나타내는 RetUser 객체의 리스트
     */
    @Override
    public List<RetUser> getAllAdmin() {
        try {
            List<User> users = adminDao.selectAllAdmin();
            return users.stream()
                    .map(this::convertToDto)
                    .toList();
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 목록 조회 중 오류가 발생했습니다.");
        }
    }

    /**
     * 주어진 유저 아이디에 대해 관리자 권한을 부여하는 메소드입니다.
     *
     * @param userId 관리자 권한을 부여할 유저의 아이디
     * @return 관리자 권한 부여가 성공한 경우 true, 실패한 경우 false
     */
    @Override
    public boolean createAdmin(int userId) {
        try {
            int rowsAffected = adminDao.createAdmin(userId);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("관리자 권한을 부여할 유저가 존재하지 않거나, 이미 관리자 권한이 부여된 유저입니다.");
            }
            return rowsAffected == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 권한 부여 중 오류가 발생했습니다.");
        }
    }

    /**
     * 주어진 유저 아이디에 대해 관리자 권한을 삭제하는 메소드입니다.
     *
     * @param userId 관리자 권한을 삭제할 유저의 아이디
     * @return 관리자 권한 삭제가 성공한 경우 true, 실패한 경우 false
     */
    @Override
    public boolean deleteAdmin(int userId) {
        try {
            int rowsAffected = adminDao.deleteAdmin(userId);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("관리자 권한을 삭제할 유저가 존재하지 않습니다.");
            }
            return rowsAffected == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("관리자 권한 삭제 중 오류가 발생했습니다.");
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
}
