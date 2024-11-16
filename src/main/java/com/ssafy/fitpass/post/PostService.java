package com.ssafy.fitpass.post;

import java.util.List;

public interface PostService {
    boolean createPost(Post post);
    Post getPost(int postId);
    List<Post> getUserPosts(int userId);
    List<Post> getAllUserPosts();
    boolean modifyPost(Post post);
    boolean removePost(int postId);
    List<Post> getFriendPosts(int userId);
}
