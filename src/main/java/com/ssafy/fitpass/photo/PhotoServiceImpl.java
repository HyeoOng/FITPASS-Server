package com.ssafy.fitpass.photo;

public class PhotoServiceImpl implements PhotoService {

    PhotoDao photoDao;

    public PhotoServiceImpl(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public boolean createPostPhoto(Photo photo) {
        return photoDao.insertPostPhoto(photo)==1;
    }

    @Override
    public boolean createProfile(int userId, Photo photo) {
        return photoDao.insertProfile(userId, photo)==1;
    }

    @Override
    public boolean modifyPostPhoto(Photo photo) {
        return photoDao.updatePostPhoto(photo)==1;
    }

    @Override
    public boolean modifyProfile(int userId, Photo photo) {
        return photoDao.updateProfile(userId, photo)==1;
    }
}
