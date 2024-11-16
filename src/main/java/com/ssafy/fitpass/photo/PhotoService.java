package com.ssafy.fitpass.photo;

public interface PhotoService {
    boolean createPostPhoto(Photo photo);
    boolean createProfile(int userId, Photo photo);
    boolean modifyPostPhoto(Photo photo);
    boolean modifyProfile(int userId, Photo photo);
}
