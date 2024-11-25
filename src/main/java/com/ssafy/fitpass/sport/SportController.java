package com.ssafy.fitpass.sport;

import com.ssafy.fitpass.exception.RegDBException;
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

    @PostMapping("/create")
    public Map<String, Object> createSport(@RequestBody Sport sport) {
        Map<String, Object> map = new HashMap<>();

        try {
            boolean result = sportService.createSport(sport);
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
            return map;
        } catch (RegDBException e) {
            map.put("flag", false);
            map.put("code", "DAL0001");
        } catch (Exception e) {
            map.put("flag", false);
            map.put("code", "SAL0002");
        }
        return map;
    }

    @PostMapping("/update")
    public Map<String, Object> updateSport(@RequestBody Sport sport) {
        Map<String, Object> map = new HashMap<>();
        try {
            boolean result = sportService.modifySport(sport);
            if (result) {
                map.put("flag", true);
            } else {
                map.put("flag", false);
            }
            return map;
        } catch (RegDBException e) {
            map.put("flag", false);
            map.put("code", "DAL0001");
        } catch (Exception e) {
            map.put("flag", false);
            map.put("code", "SAL0002");
        }
        return map;

    }

    @GetMapping
    public List<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteSport(@RequestBody Map<String, String> requestData) {
            Map<String, Object> map = new HashMap<>();
        try {
            int sportCode = Integer.parseInt(requestData.get("sportCode"));
            boolean result = sportService.removeSport(sportCode);
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
