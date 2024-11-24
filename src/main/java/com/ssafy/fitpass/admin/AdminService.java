package com.ssafy.fitpass.admin;

import com.ssafy.fitpass.user.dto.RetUser;

import java.util.List;

public interface AdminService {

    List<RetUser> getAllAdmin();
    boolean createAdmin(int userId);
    boolean deleteAdmin(int userId);
}
