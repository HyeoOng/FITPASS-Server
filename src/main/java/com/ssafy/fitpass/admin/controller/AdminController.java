package com.ssafy.fitpass.admin.controller;

import com.ssafy.fitpass.admin.service.AdminService;
import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.user.dto.RetUser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:5173")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 모든 관리자의 목록을 반환하는 API입니다. (일반 관리자, 최고 관리자 모두)
     *
     * @return 모든 관리자를 나타내는 RetUser 객체의 리스트
     */
    @GetMapping
    public List<RetUser> getAllAdmins() {
        List<RetUser> list = adminService.getAllAdmin();  // 서비스에서 모든 관리자를 조회
        return list;  // 관리자의 목록을 반환
    }

    /**
     * 유저에게 관리자 권한을 부여하는 API입니다. (최고관리자만 가능)
     *
     * @param requestData (관리자 권한을 부여할 유저의 아이디)
     * @return 결과 메시지가 담긴 Map
     */
    @PostMapping("/create")
    public Map<String, Object> createAdmin(@RequestBody Map<String, String> requestData) {
        Map<String, Object> map = new HashMap<>();
        try {
            int userId = Integer.parseInt(requestData.get("userId"));
            boolean result = adminService.createAdmin(userId); // 서비스 호출
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
        } catch (NullPointerException e) {
            map.put("flag", false);
            map.put("code", "UAL0007");
        } catch (RegDBException e) {
            map.put("code", "DAL0001"); // DAL0001
            map.put("flag", false);
        } catch (Exception e) {
            map.put("code", "SAL0002"); // SAL0002
            map.put("flag", false);
        }
        return map;
    }

    /**
     * 유저에게서 관리자 권한을 삭제하는 API입니다. (최고 관리자만 가능)
     *
     * @param requestData (관리자 권한을 삭제할 유저의 아이디)
     * @return 결과 메시지가 담긴 Map
     */
    @PostMapping("/delete")
    public Map<String, Object> deleteAdmin(@RequestBody Map<String, String> requestData) {
        Map<String, Object> map = new HashMap<>();
        try {
            int userId = Integer.parseInt(requestData.get("userId"));
            boolean result = adminService.deleteAdmin(userId); // 서비스 호출
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
        } catch (NullPointerException e) {
            map.put("flag", false);
            map.put("code", "UAL0007");
        } catch (RegDBException e) {
            map.put("code", "DAL0001"); // DAL0001
            map.put("flag", false);
        } catch (Exception e) {
            map.put("code", "SAL0002"); // SAL0002
            map.put("flag", false);
        }
        return map;
    }

}
