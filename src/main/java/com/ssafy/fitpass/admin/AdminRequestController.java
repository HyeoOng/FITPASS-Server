package com.ssafy.fitpass.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/request")
public class AdminRequestController {

    private final AdminRequestService adminRequestService;

    public AdminRequestController(AdminRequestService adminRequestService) {
        this.adminRequestService = adminRequestService;
    }

    @PostMapping("/create")
    public Map<String, String> create(@RequestBody AdminRequest adminRequest) {
        Map<String, String> map = new HashMap<>();
        boolean result = adminRequestService.createRequest(adminRequest);
        if(result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
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
