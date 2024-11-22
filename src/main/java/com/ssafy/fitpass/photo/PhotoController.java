package com.ssafy.fitpass.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//@CrossOrigin(origins = "http://localhost:5173")
public class PhotoController {

    static private final String BASE_PATH = "src/main/webapp/WEB-INF";

//    @PostMapping
//    public ResponseEntity<?> displayPhoto(@RequestBody String photoUrlJson) {
//        ObjectMapper mapper = new ObjectMapper();
//        String photoUrl = "";
//
//        try{
//            JsonNode jsonNode = mapper.readTree(photoUrlJson);
//            photoUrl = jsonNode.get("photoUrl").asText();
//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("입력받은 값: " + photoUrl);
//
//        Path file = Paths.get(BASE_PATH, photoUrl).normalize();
//        System.out.println("파일이 존재하는지 확일한 위치: " + file.toAbsolutePath().toString());
//        if (!file.startsWith(BASE_PATH)) {
//            System.out.println("경로를 탈출했어요.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("hacker"); // 경로 탈출 방지
//        }
//        Resource resource = new FileSystemResource(file.toAbsolutePath().toString());
//
//        if(!resource.exists()){
//            System.out.println("파일을 찾지 못했어요.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not found");
//        }
//        System.out.println("파일을 찾았습니다!!!!!!!!!!!!!!!!");
//        HttpHeaders headers = new HttpHeaders();
//        try {
//            headers.add("Content-Type", Files.probeContentType(file));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("완료");
//        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
//
//    }

    @PostMapping
    public ResponseEntity<?> getImage(@RequestBody String imageUrlJson){

        // keywordJson에서 "keyword" 값을 추출
        ObjectMapper keywordMapper = new ObjectMapper();
        String imageUrl = "";
        try {
            JsonNode jsonNode = keywordMapper.readTree(imageUrlJson);
            imageUrl = jsonNode.get("photoUrl").asText(); // "keyword" 값 추출
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
        try{
            // 상대 경로 검증
            // 1. resolve : 기본 경로와 추가 경로를 합쳐 새로운 하나의 경로로 만든다.(운영체제에 따라 파일 구분자를 알아서 변경해준다..)
            // 2. normalize : 경로에서 불필요하거나 비정상적인 요소를 제거하여 정규화된 경로를 반환
            // 3. startsWith : 결과가 BASE_PATH를 벗어난 경로라면 일을 수행하지 못하도록 막는다.
//            Path file = BASE_PATH.resolve(Paths.get(imageUrl)).normalize();
            Path file = Paths.get(BASE_PATH, imageUrl).normalize();
            if (!file.startsWith(BASE_PATH)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("hacker"); // 경로 탈출 방지
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
                // System.out.println("잘 반환함");
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server");
    }
}
