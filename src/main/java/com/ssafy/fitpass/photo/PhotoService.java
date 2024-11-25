package com.ssafy.fitpass.photo;

import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.exception.UserException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoDao photoDao;

    public PhotoService(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

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
    }
    
    // 사진을 실제 서버에 저장하는 이름을 생성해주는 메서드
    public static String generateStoreFileName(String originalFilename) {
        // 파일 확장자 추출
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(dotIndex); // 확장자 포함 (예: ".jpg")
        }

        // 현재 날짜 가져오기 (yyyyMMdd 형식)
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // UUID 기반 새 파일 이름 생성
        return UUID.randomUUID().toString() + formattedDate + extension;
    }

    public String getProfileFolderNameByUserId(int userId) {
        try {
            String name = photoDao.selectPhotoIdbyUserId(userId);
            if(name==null){
                throw new UserException("프로필 사진이 존재하지 않습니다.", "NP");
            }
            return name;
        } catch (DataAccessException e){
            throw new RegDBException();
        }
    }
}
