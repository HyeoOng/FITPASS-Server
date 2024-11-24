package com.ssafy.fitpass.admin;

import com.ssafy.fitpass.user.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/request")
public class AdminRequestController {

    private final AdminRequestService adminRequestService;

    public AdminRequestController(AdminRequestService adminRequestService) {
        this.adminRequestService = adminRequestService;
    }

    @PostMapping("/create")
    public Map<String, String> create(@RequestBody AdminRequest adminRequest, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        int userId = ((RetUser) session.getAttribute("user")).getUserId();
        System.out.println(userId);
        adminRequest.setUserId(userId);
        boolean result = adminRequestService.createRequest(adminRequest);
        if(result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @GetMapping
    public List<AdminRequest> getAllAdminRequest() {
        return adminRequestService.getAllRequests();
    }

    @PostMapping("/remove")
    public Map<String, String> remove(@RequestBody Map<String, Integer> requestData) {
        Map<String, String> map = new HashMap<>();
        int requestId = requestData.get("requestId");
        boolean result = adminRequestService.removeRequest(requestId);
        if (result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }
}
