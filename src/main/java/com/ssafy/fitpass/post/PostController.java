package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.place.Place;
import org.springframework.web.bind.annotation.*;

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

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Map<String, String> createPost(@RequestBody PostRequestDTO postRequest){
        Map<String, String> response = new HashMap<>();
        Post post = postRequest.getPost();
        Photo photo = postRequest.getPhoto();
        Place place = postRequest.getPlace();
        // 글 등록 전에는 항상 장소와 photo를 먼저 등록해야 한다..
        // 1. 장소 먼저 등록
        // 1-1. 장소 테이블에 등록되어 있는지 확인한다.
        int placeId = postService.getPlaceId(place.getKakaoMapId());
        if(placeId==-1){ // 등록된 장소가 아닌 경우 -> 장소를 먼저 테이블에 등록
            if(!postService.createPost(post)){
                response.put("msg_place", "장소를 등록하는 것을 실패하였습니다.");
            }
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
            String uploadName = photo.getUploadFileName();
            String storeName = UUID.randomUUID().toString() + "_" + uploadName; // UUID와 원래 파일명을 결합하여 고유 이름 생성
            // 2-2-3. 사진을 저장하는 폴더 경로 지정
            String saveFolder = "/post" + postId + "/" + storeName;
            // 2-2-4. photo 객체에 담아 사진 등록
            photo.setStoreFileName(storeName);
            photo.setSaveFolder(saveFolder);
            
            if(postService.createPostPhoto(photo)){
                // 2-2-5. 실제 서버에 사진 저장(src/main/webapp/WEB-INF/post/postId)
                // 파일이 저장될 경로 설정
                Path uploadPath = Paths.get("src/main/webapp/WEB-INF/post/" + postId);
                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);  // 디렉토리 없으면 생성
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.put("msg", "파일 저장 경로 생성에 실패했습니다.");
                        return response;
                    }
                }

                // 실제로 파일 저장
//                try {
//                    File fileToSave = new File(uploadPath.toFile(), storeName);
//                    // photo.getUploadFileName().transferTo(fileToSave);  // 업로드된 파일을 저장
//                    response.put("msg", "글 등록에 성공하였습니다.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    response.put("msg", "사진 저장에 실패했습니다.");
//                }
                response.put("msg", "글 등록에 성공하였습니다.");
            }else{
                response.put("msg", "글은 등록했지만 이미지 등록에는 실패하였습니다.");
            }
        }else{ // 글 등록 실패
            response.put("msg", "글 등록에 실패하였습니다. <br> 잠시 후에 다시 시도해주세요.");
        }
        
        // 3. src/main/webapp/WEB-INF/post/postId에 사진 저장
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
