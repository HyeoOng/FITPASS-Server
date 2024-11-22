package com.ssafy.fitpass.admin;

import com.ssafy.fitpass.user.RetUser;
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
     * @return 결과 메시지가 담긴 Map("success" 또는 "fail")
     */
    @PostMapping("/create")
    public Map<String, String> createAdmin(@RequestBody Map<String, String> requestData) {
        int userId = Integer.parseInt(requestData.get("userId"));
        boolean result = adminService.createAdmin(userId);  // 서비스에서 관리자 권한을 부여하는 로직 호출
        Map<String, String> map = new HashMap<>();  // 결과 메시지를 담을 Map 객체 초기화
        if (result) {
            map.put("msg", "success");  // 성공 시 "success" 메시지 추가
        } else {
            map.put("msg", "fail");  // 실패 시 "fail" 메시지 추가
        }
        return map;  // 결과 메시지가 담긴 Map을 반환
    }

    /**
     * 유저에게서 관리자 권한을 삭제하는 API입니다. (최고 관리자만 가능)
     *
     * @param requestData (관리자 권한을 삭제할 유저의 아이디)
     * @return 결과 메시지가 담긴 Map("success" 또는 "fail")
     */
    @PostMapping("/delete")
    public Map<String, String> deleteAdmin(@RequestBody Map<String, String> requestData) {
        int userId = Integer.parseInt(requestData.get("userId"));
        boolean result = adminService.deleteAdmin(userId);  // 서비스에서 관리자 권한을 삭제하는 로직 호출
        Map<String, String> map = new HashMap<>();  // 결과 메시지를 담을 Map 객체 초기화
        if (result) {
            map.put("msg", "success");  // 성공 시 "success" 메시지 추가
        } else {
            map.put("msg", "fail");  // 실패 시 "fail" 메시지 추가
        }
        return map;  // 결과 메시지가 담긴 Map을 반환
    }

}
