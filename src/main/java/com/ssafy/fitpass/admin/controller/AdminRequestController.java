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
    public Map<String, Object> create(@RequestBody PostAdminRequestDto adminRequest, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);

        try {
            int userId = ((RetUser) session.getAttribute("user")).getUserId();

            adminRequest.setUserId(userId);
            boolean result = adminRequestService.createRequest(adminRequest);
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
            return map;
        } catch (RegDBException e) {
            map.put("code", "DAL0001"); // DAL0001
            map.put("flag", false);
        } catch (Exception e) {
            map.put("code", "SAL0002"); // SAL0002
            map.put("flag", false);
        }
        return map;
    }

    @GetMapping
    public List<RetAdminRequestDto> getAllAdminRequest() {
        return adminRequestService.getAllRequests();
    }

    @PostMapping("/remove")
    public Map<String, Object> remove(@RequestBody Map<String, Integer> requestData) {
        Map<String, Object> map = new HashMap<>();
        try {
            int requestId = requestData.get("requestId");
            boolean result = adminRequestService.removeRequest(requestId);
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
            return map;
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
