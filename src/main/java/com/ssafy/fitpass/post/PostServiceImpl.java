package com.ssafy.fitpass.post;

import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.photo.PhotoDao;
import com.ssafy.fitpass.place.Place;
import com.ssafy.fitpass.place.PlaceDao;
import com.ssafy.fitpass.post.dto.PutPostDto;
import org.springframework.dao.DataAccessException;
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
    public boolean createPost(PutPostDto post) {
        try{
            return postDao.insertPost(post)==1;
        } catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public Post getPost(int postId) {
        try{
            return postDao.selectOne(postId);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public Page<Post> getUserPosts(int userId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        try{
            List<Post> postList = postDao.selectUserPost(userId, offset, size);
            int total = postDao.totalMyPostNum(userId);
            return new PageImpl<>(postList, pageable, total);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public Page<Post> getAllUserPosts(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        try{
            List<Post> postList = postDao.selectAll(offset, size);
            int total = postDao.totalPostNum();
            return new PageImpl<>(postList, pageable, total);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }

    }

    @Override
    public boolean modifyPost(Post post) {
        try{
            return postDao.updatePost(post)==1;
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public boolean removePost(int postId) {
        try{
            return postDao.deletePost(postId)==1;
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public Page<Post> getFriendPosts(int userId, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        try{
            List<Post> postList = postDao.selectFriendPosts(userId, offset, size);
            int total = postDao.totalMyFriendsPostNum(userId);
            return new PageImpl<>(postList, pageable, total);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public List<Post> getUserAllPosts(int userId) {
        try{
            return postDao.selectUserAllPosts(userId);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public int getPostId(Post post){
        try{
            return postDao.selectPostId(post);
        } catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public Place getPlace(int postId) {
        try{
            return placeDao.selectOne(postId);
        }catch (DataAccessException e){
            throw new RegDBException(); // DAL0001
        }
    }

    @Override
    public int getPlaceId(Place place) {
        try{
            Integer result = placeDao.selectPlaceId(place);
            if(result!=null) {
                return result;
            }
        }catch (DataAccessException e){
            throw new RegDBException();
        }
        return -1;
    }

    @Override
    public boolean createPlace(Place place) {
        try{
            return placeDao.insertPlace(place)==1;
        } catch (DataAccessException e){
            throw new RegDBException();
        }
    }

    @Override
    public boolean createPostPhoto(Photo photo) {
        try{
            return photoDao.insertPostPhoto(photo)==1;
        }catch (DataAccessException e){
            throw new RegDBException();
        }
    }

    @Override
    public boolean modifyPostPhoto(Photo photo) {
        try{
            return photoDao.updatePostPhoto(photo)==1;
        } catch (DataAccessException e){
            throw new RegDBException();
        }
    }

    @Override
    public int getPhotoId(Photo photo) {
        try{
            return photoDao.selectPhotoId(photo);
        } catch (DataAccessException e){
            throw new RegDBException();
        }
    }
}
