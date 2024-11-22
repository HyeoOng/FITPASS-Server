package com.ssafy.fitpass.admin;

import com.ssafy.fitpass.user.RetUser;

import java.util.List;

public interface AdminService {

    List<RetUser> getAllAdmin();
    boolean createAdmin(int userId);
    boolean deleteAdmin(int userId);
}
