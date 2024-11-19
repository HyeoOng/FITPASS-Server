package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoService;
import com.ssafy.fitpass.place.Place;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
public class PostController {

    PostService postService;
    PhotoService photoService;

    public PostController(PostService postService, PhotoService photoService) {
        this.photoService = photoService;
        this.postService = postService;
    }

    @PostMapping
    public Map<String, String> createPost(@RequestPart("post") Post post,
                                          @RequestPart("place") Place place,
                                          @RequestPart("file") MultipartFile file) {
        System.out.println("data 잘 받았는지 확인해보자-------------------");
        System.out.println(post);
        System.out.println(place);
        // System.out.println(file.getOriginalFilename());
        System.out.println("------------------------------------------");

        Map<String, String> response = new HashMap<>();

        Photo photo = new Photo();
        if(!file.isEmpty())photo.setFile(file);
        else {
            response.put("msg", "사진 이상");
            return response;
        }

        // 글 등록 전에는 항상 장소를 먼저 등록해야 한다..
        // 1. 장소 먼저 등록
        // 1-1. 장소 테이블에 등록되어 있는지 확인한다.
        int placeId = postService.getPlaceId(place);
        System.out.println("placeId: "+placeId);
        if(placeId==-1){ // 등록된 장소가 아닌 경우 -> 장소를 먼저 테이블에 등록
            if(!postService.createPlace(place)){
                response.put("msg_place", "장소를 등록하는 것을 실패하였습니다.");
                return response;
            }
            placeId = postService.getPlaceId(place); // 등록된 placeId 다시 가져오기
            System.out.println("내가 등록한 placeId: "+placeId);
        }

        // 2. 글을 등록한다.
        // 2-1. post에 placeId를 등록한다.
        post.setPlaceId(placeId);
        // 2-2. 글 등록
        if(postService.createPost(post)){
            // 2-2-1. 글 등록을 성공하면.. postId를 받아서 사진을 등록한다.
            int postId = postService.getPostId(post);
            photo.setPostId(postId);
            // 2-2-2. 업로드 파일명을 이용해 UUID를 만들어 storeFileName으로 지정
            String storeName = photoService.generateStoreFileName(photo.getUploadFileName());
            // 2-2-3. 사진을 저장하는 폴더 경로 지정
            String saveFolder = "/post/" + postId + "/" + storeName;
            // 2-2-4. photo 객체에 담아 사진 등록
            photo.setStoreFileName(storeName);
            photo.setSaveFolder(saveFolder);
            System.out.println(photo);
            
            if(postService.createPostPhoto(photo)){
                // 2-2-5. 실제 서버에 사진 저장(src/main/webapp/WEB-INF/post/postId)
                try {
                    photoService.saveFile(file, postId, storeName, "post/");
                } catch (IOException e) {
                    response.put("msg", "파일 저장에 실패하였습니다. 잠시 후 다시 시도해주세요.");
                    return response; // 실패 시 바로 반환
                }
                response.put("msg", "글 등록에 성공하였습니다.");
            }else{
                response.put("msg", "글은 등록했지만 이미지 등록에는 실패하였습니다.");
            }
        }else{ // 글 등록 실패
            response.put("msg", "글 등록에 실패하였습니다. <br> 잠시 후에 다시 시도해주세요.");
        }
        return response;
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable int postId){
        return postService.getPost(postId);
    }

    @GetMapping("/user/{userId}")
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
    public Map<String, String> removePost(@RequestBody Map<String, Integer> requestBody){
        Map<String, String> response = new HashMap<>();

        int postId = requestBody.get("postId");
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
