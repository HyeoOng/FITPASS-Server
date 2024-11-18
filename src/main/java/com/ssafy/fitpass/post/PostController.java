package com.ssafy.fitpass.post;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Map<String, String> createPost(@RequestBody Post post){
        Map<String, String> response = new HashMap<>();
//        postService.createPlace()
        if(postService.createPost(post)){
            response.put("msg", "글을 성공적으로 등록하였습니다.");
        }else {
            response.put("msg", "죄송합니다. 글 등록에 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable int postId){
        return postService.getPost(postId);
    }

    @GetMapping("/{userId}")
    public List<Post> getUserPosts(@PathVariable int userId){
        return postService.getUserPosts(userId);
    }

    @GetMapping
    public List<Post> getAllUserPosts(){
        return postService.getAllUserPosts();
    }

    @PostMapping("/update")
    public Map<String, String> modifyPost(@RequestBody Post post){
        Map<String, String> response = new HashMap<>();
        if(postService.modifyPost(post)){
            response.put("msg", "글을 성공적으로 수정하였습니다.");
        }else{
            response.put("msg", "글 수정에 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @PostMapping("/delete")
    public Map<String, String> removePost(@RequestBody int postId){
        Map<String, String> response = new HashMap<>();
        if(postService.removePost(postId)){
            response.put("msg", "글을 성공적으로 삭제하였습니다.");
        }else{
            response.put("msg", "글 삭제를 실패하였습니다. <br> 잠시 후 다시 시도해주세요.");
        }
        return response;
    }

    @GetMapping("/friend")
    public List<Post> getFriendPosts(@RequestParam int userId){
        return postService.getFriendPosts(userId);
    }
}
