package com.ssafy.fitpass.photo;

import org.springframework.web.multipart.MultipartFile;

public class Photo {
    private int id, postId;
    private String uploadFileName, storeFileName, saveFolder;
    private MultipartFile file;

    public Photo() {}

    public void setPostId(int postId) { this.postId = postId; }

    public int getPostId() { return postId;}

    public void setId(int id) {
        this.id = id;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    public int getId() {
        return id;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public String getSaveFolder() {
        return saveFolder;
    }

    public MultipartFile getFile() { return file;}

    public void setFile(MultipartFile file) {
        setUploadFileName(file.getOriginalFilename());
        this.file = file;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", postId=" + postId +
                ", uploadFileName='" + uploadFileName + '\'' +
                ", storeFileName='" + storeFileName + '\'' +
                ", saveFolder='" + saveFolder + '\'' +
                '}';
    }
}
