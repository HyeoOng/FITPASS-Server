package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.place.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    boolean createPost(Post post);
    Post getPost(int postId);
    Page<Post> getUserPosts(int userId, Pageable pageable);
    Page<Post> getAllUserPosts(Pageable pageable);
    boolean modifyPost(Post post);
    boolean removePost(int postId);
    Page<Post> getFriendPosts(int userId, Pageable pageable);
    int getPostId(Post post);

    Place getPlace(int postId);
    int getPlaceId(Place place);
    boolean createPlace(Place place);

    boolean createPostPhoto(Photo photo);
    boolean modifyPostPhoto(Photo photo);
    int getPhotoId(Photo photo);
    int getProfileId(int userId);
}
