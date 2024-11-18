package com.ssafy.fitpass.photo;

import org.springframework.stereotype.Service;

@Service
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
    public boolean modifyPostPhoto(Photo photo) {
        return photoDao.updatePostPhoto(photo)==1;
    }

}
