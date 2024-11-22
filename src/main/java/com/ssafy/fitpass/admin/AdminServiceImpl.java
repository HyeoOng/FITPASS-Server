package com.ssafy.fitpass.admin;

import com.ssafy.fitpass.user.RetUser;
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
        return adminDao.selectAllAdmin();  // AdminDao에서 모든 관리자 정보를 조회
    }

    /**
     * 주어진 유저 아이디에 대해 관리자 권한을 부여하는 메소드입니다.
     *
     * @param userId 관리자 권한을 부여할 유저의 아이디
     * @return 관리자 권한 부여가 성공한 경우 true, 실패한 경우 false
     */
    @Override
    public boolean createAdmin(int userId) {
        return adminDao.createAdmin(userId) == 1;  // AdminDao에서 권한 부여 작업을 수행하고 성공 여부를 반환
    }

    /**
     * 주어진 유저 아이디에 대해 관리자 권한을 삭제하는 메소드입니다.
     *
     * @param userId 관리자 권한을 삭제할 유저의 아이디
     * @return 관리자 권한 삭제가 성공한 경우 true, 실패한 경우 false
     */
    @Override
    public boolean deleteAdmin(int userId) {
        return adminDao.deleteAdmin(userId) == 1;  // AdminDao에서 권한 삭제 작업을 수행하고 성공 여부를 반환
    }
}
