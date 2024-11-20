package com.ssafy.fitpass.photo;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
//@CrossOrigin(origins = "http://localhost:5173")
public class PhotoController {

    static private Path BASE_PATH = Paths.get("src/webapp/WEB-INF");

    @PostMapping
    public ResponseEntity<?> getImage(@RequestParam("photoUrl") String imageUrl){
        try{
            // 상대 경로 검증
            // 1. resolve : 기본 경로와 추가 경로를 합쳐 새로운 하나의 경로로 만든다.(운영체제에 따라 파일 구분자를 알아서 변경해준다..)
            // 2. normalize : 경로에서 불필요하거나 비정상적인 요소를 제거하여 정규화된 경로를 반환
            // 3. startsWith : 결과가 BASE_PATH를 벗어난 경로라면 일을 수행하지 못하도록 막는다.
            Path file = BASE_PATH.resolve(imageUrl).normalize();
            if (!file.startsWith(BASE_PATH)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("hacker"); // 경로 탈출 방지
            }
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server");
    }
}
