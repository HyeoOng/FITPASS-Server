package com.ssafy.fitpass.friend;

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
    public Map<String, String> sendFriendRequest(@RequestBody Friend friend) {
        boolean result = friendService.sendFriendRequest(friend.getFromUser(), friend.getToUSer());
        Map<String, String> map = new HashMap<>();

        if (result) {
            map.put("msg", "success");
        } else{
            map.put("msg", "fail");
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
    public Map<String, String> deleteFriendRequest(@RequestBody Friend friend) {
        Map<String, String> map = new HashMap<>();
        System.out.println("delete from: " + friend.getFromUser());
        boolean result = friendService.deleteFriendRequest(friend.getFromUser(), friend.getToUSer());
        if (result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @PostMapping("/delete")
    public Map<String, String> deleteFriend(@RequestBody Friend friend) {
        Map<String, String> map = new HashMap<>();
        boolean result = friendService.deleteFriend(friend.getFromUser(), friend.getToUSer());
        if (result) {
            map.put("msg", "success");
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

}
