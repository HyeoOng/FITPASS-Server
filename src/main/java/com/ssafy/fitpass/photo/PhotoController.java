package com.ssafy.fitpass.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
//@CrossOrigin(origins = "http://localhost:5173")
public class PhotoController {

    static private final Path BASE_PATH = Paths.get("src/webapp/WEB-INF").toAbsolutePath().normalize();;

    @PostMapping
    public ResponseEntity<?> getImage(@RequestBody String imageUrlJson){
//        System.out.println("photoUrl : "+imageUrl);

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
            Path file = Paths.get(BASE_PATH+imageUrl).normalize();
            System.out.println("base_path"+ BASE_PATH);
            System.out.println("file: "+file.toAbsolutePath());
            if (!file.startsWith(BASE_PATH)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("hacker"); // 경로 탈출 방지
            }
//            Resource resource = new UrlResource(file.toUri());
            if(Files.exists(file)){System.out.println("일단 파일은 존재해요.");}
            else {
                System.out.println("존재하지 않아요..");
            }

            if (Files.exists(file) && Files.isReadable(file)) {
                System.out.println("readable");
                Resource resource = new UrlResource(file.toUri());
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server1");
            }

//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok().body(resource);
//            }else{
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server1");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server2");
    }
}
