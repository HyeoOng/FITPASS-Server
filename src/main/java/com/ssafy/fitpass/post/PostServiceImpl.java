package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoDao;
import com.ssafy.fitpass.place.Place;
import com.ssafy.fitpass.place.PlaceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    PostDao postDao;
    PlaceDao placeDao;
    PhotoDao photoDao;

    public PostServiceImpl(PostDao postDao, PlaceDao placeDao, PhotoDao photoDao) {
        this.postDao = postDao;
        this.placeDao = placeDao;
        this.photoDao = photoDao;
    }

    @Override
    public boolean createPost(Post post) {
        return postDao.insertPost(post)==1;
    }

    @Override
    public Post getPost(int postId) {
        return postDao.selectOne(postId);
    }

    @Override
    public Page<Post> getUserPosts(int userId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        List<Post> postList = postDao.selectUserPost(userId, offset, size);
        int total = postDao.totalMyPostNum(userId);
        return new PageImpl<>(postList, pageable, total);
    }

    @Override
    public Page<Post> getAllUserPosts(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        List<Post> postList = postDao.selectAll(offset, size);
        int total = postDao.totalPostNum();
        return new PageImpl<>(postList, pageable, total);
    }

    @Override
    public boolean modifyPost(Post post) {
        return postDao.updatePost(post)==1;
    }

    @Override
    public boolean removePost(int postId) {
        return postDao.deletePost(postId)==1;
    }

    @Override
    public Page<Post> getFriendPosts(int userId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        List<Post> postList = postDao.selectFriendPosts(userId, offset, size);
        int total = postDao.totalMyFriendsPostNum(userId);
        return new PageImpl<>(postList, pageable, total);
    }

    @Override
    public int getPostId(Post post){
        return postDao.selectPostId(post);
    }

    @Override
    public Place getPlace(int postId) {
        return placeDao.selectOne(postId);
    }

    @Override
    public int getPlaceId(Place place) {
        Integer result = placeDao.selectPlaceId(place);
        if(result==null){
            return -1;
        }
        return result;
    }

    @Override
    public boolean createPlace(Place place) {
        return placeDao.insertPlace(place)==1;
    }

    @Override
    public boolean createPostPhoto(Photo photo) {
        return photoDao.insertPostPhoto(photo)==1;
    }

    @Override
    public boolean modifyPostPhoto(Photo photo) {
        return photoDao.updatePostPhoto(photo)==1;
    }

    @Override
    public int getPhotoId(Photo photo) {
        return photoDao.selectPhotoId(photo);
    }

    @Override
    public int getProfileId(int userId) {
        return photoDao.selectPhotoIdbyUserId(userId);
    }
}
