package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.place.Place;

import java.util.List;

public interface PostService {
    boolean createPost(Post post);
    Post getPost(int postId);
    List<Post> getUserPosts(int userId, int page, int size);
    List<Post> getAllUserPosts();
    boolean modifyPost(Post post);
    boolean removePost(int postId);
    List<Post> getFriendPosts(int userId);
    int getPostId(Post post);

    Place getPlace(int postId);
    int getPlaceId(Place place);
    boolean createPlace(Place place);

    boolean createPostPhoto(Photo photo);
    boolean modifyPostPhoto(Photo photo);
    int getPhotoId(Photo photo);
    int getProfileId(int userId);
}
