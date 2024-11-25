package com.ssafy.fitpass.admin.controller;

import com.ssafy.fitpass.admin.dto.PostAdminRequestDto;
import com.ssafy.fitpass.admin.dto.RetAdminRequestDto;
import com.ssafy.fitpass.admin.service.AdminRequestService;
import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.user.dto.RetUser;
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
    public Map<String, String> create(@RequestBody PostAdminRequestDto adminRequest, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);

        try {
            int userId = ((RetUser) session.getAttribute("user")).getUserId();

            adminRequest.setUserId(userId);
            boolean result = adminRequestService.createRequest(adminRequest);
            if(result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
            return map;
        } catch (RegDBException e) {
            // DAL0001

        } catch (Exception e) {
            // SAL0002
        }
        return map;
    }

    @GetMapping
    public List<RetAdminRequestDto> getAllAdminRequest() {

        try {
            return adminRequestService.getAllRequests();
        } catch (RegDBException e) {
            // DAL0001
        } catch (Exception e) {
            // SAL0002
        }
        return null;
    }

    @PostMapping("/remove")
    public Map<String, String> remove(@RequestBody Map<String, Integer> requestData) {
        Map<String, String> map = new HashMap<>();
        try {
            int requestId = requestData.get("requestId");
            boolean result = adminRequestService.removeRequest(requestId);
            if (result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
            return map;
        } catch (RegDBException e) {
            // DAL0001

        } catch (Exception e) {
            // SAL0002

        }
        return map;
    }
}
