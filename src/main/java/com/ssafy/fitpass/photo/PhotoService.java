package com.ssafy.fitpass.photo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    // 실제 파일을 서버에 저장하는 메서드
    public void saveFile(MultipartFile file, int id, String fileName, String folderName) throws IOException {
        // 1. 파일 저장 경로 생성
        Path folderPath = Paths.get("src/main/webapp/WEB-INF/" + folderName + id);  // 저장할 폴더 경로
        Path filePath = folderPath.resolve(fileName);  // 파일명으로 저장할 경로

        // 2. 디렉토리가 존재하지 않으면 생성
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);  // 폴더가 없으면 생성
        }

        // 3. 파일을 지정한 경로에 저장
        file.transferTo(filePath);  // 파일을 해당 경로에 저장

        // 4. 저장된 파일의 절대 경로 반환
        // return filePath.toString();
    }
}
