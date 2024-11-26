package com.ssafy.fitpass.post;

import com.ssafy.fitpass.ai.service.AiService;
import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.exception.RegFDException;
import com.ssafy.fitpass.exception.UserException;
import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoService;
import com.ssafy.fitpass.place.Place;
import com.ssafy.fitpass.user.dto.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    PostService postService;
    PhotoService photoService;
    AiService aiService;

    public PostController(PostService postService, PhotoService photoService, AiService aiService) {
        this.photoService = photoService;
        this.postService = postService;
        this.aiService = aiService;
    }
    
    // 글 등록
    @PostMapping
    public Map<String, Object> createPost(@RequestPart("post") Post post,
                                          @RequestPart("place") Place place,
                                          @RequestPart("file") MultipartFile file,
                                          HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        if(file.isEmpty()){
            response.put("flag", false);
            response.put("code", "UAL0003"); // UAL0003
            return response;
        }

        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }

            int userId = retUser.getUserId();
            post.setUserId(userId);


            Photo photo = new Photo();

            // 이미지 MIME 타입 검증
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("flag", false);
                response.put("code", "UAL0006"); // 잘못된 시도
                return response;
            }

            // 파일 확장자 확인
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.matches(".*\\.(jpg|jpeg|png|gif|bmp|jfif|PNG)$")) {
                response.put("flag", false);
                response.put("code", "UAL0006"); // 잘못된 시도
                return response;
            }

            long maxFileSize = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > maxFileSize) {
                response.put("flag", false);
                response.put("code", "UAL0006"); // 잘못된 시도
                return response;
            }

            photo.setFile(file);

            // Post 객체의 title과 content 검증
            if (!aiService.isContentAppropriate(post.getContent()) || !aiService.isContentAppropriate(post.getTitle())) {
                response.put("flag", false);
                response.put("code", "UAL0005");
                return response;
            }

            // 글 등록 전에는 항상 장소를 먼저 등록해야 한다..
            // 1. 장소 먼저 등록
            // 1-1. 장소 테이블에 등록되어 있는지 확인한다.
            int placeId = postService.getPlaceId(place);
            if(placeId==-1){ // 등록된 장소가 아닌 경우 -> 장소를 먼저 테이블에 등록
                if(!postService.createPlace(place)){
                    response.put("flag", false);
                    response.put("code", "DAL0001");
                    return response;
                }
                placeId = postService.getPlaceId(place); // 등록된 placeId 다시 가져오기
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

                if(postService.createPostPhoto(photo)){
                    // 2-2-5. 실제 서버에 사진 저장(src/main/webapp/WEB-INF/post/postId)
                    try {
                        photoService.saveFile(file, postId, storeName, "post/");
                    } catch (IOException e) {
                        response.put("flag", false);
                        response.put("code", "SAL0001"); // SAL0001
                        return response; // 실패 시 바로 반환
                    }
                    response.put("flag", true);
                }else{
                    response.put("flag", false);
                    response.put("code", "DAL0001"); // DAL0001
                    return response;
                }
            }else{ // 글 등록 실패
                response.put("flag", false);
                response.put("code", "DAL0001"); // DAL0001
            }
        } catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (RegFDException e) {
            response.put("flag", false);
            response.put("code", "SAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }

        return response;
    }

    @GetMapping("/{postId}")
    public Map<String, Object> getPost(@PathVariable int postId){
        Map<String, Object> response = new HashMap<>();
        if(postId == 0){
            response.put("flag", false);
            response.put("code", "SPL0001");
        }

        try{
            Post post = postService.getPost(postId);
            response.put("flag", true);
            response.put("post", post);
            return response;
        }catch (UserException e){
            response.put("flag", false);
            if(e.getErrorCode().equals("NI")){
                response.put("code", "UAL0007");
            }
            return response;
        } catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e){
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    // 사용자 글 조회Page<Post>
    @GetMapping("/user")
    public Map<String, Object> getUserPosts(
            @RequestParam(defaultValue = "0") int page, // 요청 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "8") int size, // 페이지당 게시글 개수 (기본값: 8)
            HttpServletRequest request
    ){
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }
            int userId = retUser.getUserId();

//            System.out.println("userId: "+userId + ", page: "+page+", size: "+size);
            Pageable pageable = PageRequest.of(page, size);

            Page<Post> postPage = postService.getUserPosts(userId, pageable);
            response.put("flag", true);
            response.put("posts", postPage);
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @GetMapping("/user-all")
    public Map<String, Object> getUserAllPosts(HttpServletRequest request){
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }
            int userId = retUser.getUserId();
            List<Post> posts = postService.getUserAllPosts(userId);
            response.put("flag", true);
            response.put("posts", posts);
            return response;
        }catch (UserException e){
            response.put("flag", false);
            if(e.getErrorCode().equals("NI")){
                response.put("code", "UAL0007");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/update")
    public Map<String, Object> modifyPost(@RequestBody Post post){
        Map<String, Object> response = new HashMap<>();
        try{
            if(postService.modifyPost(post)){
                response.put("flag", true);
            }else{
                response.put("flag", false);
                response.put("code", "DAL0001");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @PostMapping("/delete")
    public Map<String, Object> removePost(@RequestBody Map<String, Integer> requestBody){
        Map<String, Object> response = new HashMap<>();

        int postId = requestBody.get("postId");
        try{
            if(postService.removePost(postId)){
                response.put("flag", true);
            }else{
                response.put("flag", false);
                response.put("code", "DAL0001");
            }
            return response;
        } catch (UserException e){
            response.put("flag", false);
            if(e.getErrorCode().equals("NI")){
                response.put("code", "UAL0007");
            }
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @GetMapping("/friend")
    public Map<String, Object> getFriendPosts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page, // 요청 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "16") int size // 페이지당 게시글 개수 (기본값: 16)
    ){
        Map<String, Object> response = new HashMap<>();
        HttpSession session = request.getSession(false);
        if(session == null){
            response.put("flag", false);
            response.put("code", "SPL0001");
            return response;
        }
        try{
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                response.put("flag", false);
                response.put("code", "SPL0001");
                return response;
            }
            int userId = retUser.getUserId();

            Pageable pageable = PageRequest.of(page, size);
            Page<Post> postPage = postService.getFriendPosts(userId, pageable);
            response.put("flag", true);
            response.put("posts", postPage);
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

    @GetMapping
    public Map<String, Object> getAllUserPosts(
            @RequestParam(defaultValue = "0") int page, // 요청 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "16") int size // 페이지당 게시글 개수 (기본값: 16)
    ){
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        try{
            Page<Post> postPage = postService.getAllUserPosts(pageable);
            response.put("flag", true);
            response.put("posts", postPage);
            return response;
        }catch (RegDBException e){
            response.put("flag", false);
            response.put("code", "DAL0001");
            return response;
        } catch (Exception e) {
            response.put("flag", false);
            response.put("code", "SAL0002");
            return response;
        }
    }

}
