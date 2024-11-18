package com.ssafy.fitpass.sport;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sport")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping
    public Map<String, String> createSport(@RequestBody Sport sport) {
        Map<String, String> map = new HashMap<>();

        try {
            boolean result = sportService.createSport(sport);
            if (result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        } catch (IllegalArgumentException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String, String> updateSport(@RequestBody Sport sport) {
        Map<String, String> map = new HashMap<>();
        try {
            boolean result = sportService.modifySport(sport);
            if (result) {
                map.put("msg", "success");
            } else {
                map.put("msg", "fail");
            }
        } catch (IllegalArgumentException e) {
            map.put("msg", e.getMessage());
        }
        return map;

    }

    @GetMapping
    public List<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    @PostMapping("/delete")
    public Map<String, String> deleteSport(@RequestBody int sportCode) {
        Map<String, String> map = new HashMap<>();
        boolean result = sportService.removeSport(sportCode);
        if (result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

}
