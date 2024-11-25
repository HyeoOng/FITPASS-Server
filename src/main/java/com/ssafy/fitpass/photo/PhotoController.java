package com.ssafy.fitpass.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fitpass.exception.UserException;
import com.ssafy.fitpass.user.dto.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
public class PhotoController {

    static private final String BASE_PATH = "src/main/webapp/WEB-INF";
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<?> getImage(@RequestBody String imageUrlJson){

        // keywordJson에서 "keyword" 값을 추출
        ObjectMapper keywordMapper = new ObjectMapper();
        String imageUrl = "";
        try {
            JsonNode jsonNode = keywordMapper.readTree(imageUrlJson);
            imageUrl = jsonNode.get("photoUrl").asText(); // "keyword" 값 추출

            // 상대 경로 검증
            // 1. resolve : 기본 경로와 추가 경로를 합쳐 새로운 하나의 경로로 만든다.(운영체제에 따라 파일 구분자를 알아서 변경해준다..)
            // 2. normalize : 경로에서 불필요하거나 비정상적인 요소를 제거하여 정규화된 경로를 반환
            // 3. startsWith : 결과가 BASE_PATH를 벗어난 경로라면 일을 수행하지 못하도록 막는다.
//            Path file = BASE_PATH.resolve(Paths.get(imageUrl)).normalize();
            Path file = Paths.get(BASE_PATH, imageUrl).normalize();
            if (!file.startsWith(BASE_PATH)) {
                return ResponseEntity.ok("HK"); // 경로 탈출 방지
            }
            Resource resource = null;
            if (Files.exists(file) && Files.isReadable(file)) {
                // System.out.println("어떤 경로일려나..: " + file.toUri());
                resource = new UrlResource(file.toUri());
                HttpHeaders headers = new HttpHeaders();
                try {
                    headers.add("Content-Type", Files.probeContentType(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.ok("NF");
            }
        }  catch (JsonProcessingException e) {
            return ResponseEntity.ok("JAL1");
        } catch (Exception e) {
            return ResponseEntity.ok("SAL2");
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getProfileImage(HttpServletRequest request, @PathVariable("user_id") int user_id){
        HttpSession session = request.getSession(false);
        if(session == null){
            return ResponseEntity.ok("SPL1");
        }
        if(user_id == 0){
            RetUser retUser = (RetUser) session.getAttribute("user");
            if(retUser == null){
                return ResponseEntity.ok("SPL1");
            }
            user_id = retUser.getUserId();
        }
        try {
            String profileUrl = photoService.getProfileFolderNameByUserId(user_id);
            if (profileUrl == null) {
                return ResponseEntity.ok("NF");
            }

            Path file = Paths.get(BASE_PATH, profileUrl).normalize();
            if (!file.startsWith(BASE_PATH)) {
                return ResponseEntity.ok("HK"); // 경로 탈출 방지
            }
            Resource resource = null;
            if (Files.exists(file) && Files.isReadable(file)) {
                resource = new UrlResource(file.toUri());
                HttpHeaders headers = new HttpHeaders();
                try {
                    headers.add("Content-Type", Files.probeContentType(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.ok("NF");
            }
        } catch (UserException e) {
            if(e.getErrorCode().equals("NP")){
                return ResponseEntity.ok("NF");
            }
            return ResponseEntity.ok("SAL2");
        } catch (Exception e) {
           return ResponseEntity.ok("SAL2");
        }
    }
}