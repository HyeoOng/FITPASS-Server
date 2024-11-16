package com.ssafy.fitpass.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    PostDao postDao;

    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
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
    public List<Post> getUserPosts(int userId) {
        return postDao.selectUserPost(userId);
    }

    @Override
    public List<Post> getAllUserPosts() {
        return postDao.selectAll();
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
    public List<Post> getFriendPosts(int userId) {
        return postDao.selectFriendPosts(userId);
    }
}
