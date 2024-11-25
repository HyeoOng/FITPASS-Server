package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.user.dto.RetUser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend")
@CrossOrigin("http://localhost:5173")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping
    public Map<String, Object> sendFriendRequest(@RequestBody Friend friend) {
        Map<String, Object> map = new HashMap<>();

        try {
            boolean result = friendService.sendFriendRequest(friend.getFromUser(), friend.getToUSer());

            if (result) {
                map.put("flag", true);
            } else{
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

    @GetMapping("/{userId}")
    public List<RetUser> getFriends(@PathVariable int userId) {
        List<RetUser> friends = friendService.getFriends(userId);
        return friends;
    }

    @GetMapping("/request/{userId}")
    public List<RetUser> getFriendsRequest(@PathVariable int userId) {
        List<RetUser> friends = friendService.getFriendRequests(userId);
        return friends;
    }

    @PostMapping("/request/delete")
    public Map<String, Object> deleteFriendRequest(@RequestBody Friend friend) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("delete from: " + friend.getFromUser());
            boolean result = friendService.deleteFriendRequest(friend.getFromUser(), friend.getToUSer());
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

    @PostMapping("/delete")
    public Map<String, Object> deleteFriend(@RequestBody Friend friend) {
        Map<String, Object> map = new HashMap<>();
        try {
            boolean result = friendService.deleteFriend(friend.getFromUser(), friend.getToUSer());
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

}
