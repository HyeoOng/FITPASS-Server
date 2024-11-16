package com.ssafy.fitpass.post;

import java.util.Date;

public class Post {
    private int postId;
    private int userId;
    private int placeId;
    private int photoId;
    private int sportCode;
    private String title;
    private Date createdAt;
    private Date updatedAt;
    private int exerciseDuration;
    private String content;
    private int isPublic;
    private String photoUrl;

    public Post() {
    }

    public Post(int userId, int placeId, int photoId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content) {
        setUserId(userId);
        setPlaceId(placeId);
        setPhotoId(photoId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
    }


    public Post(int postId, int userId, int placeId, int photoId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content, int isPublic) {
        setPostId(postId);
        setUserId(userId);
        setPlaceId(placeId);
        setPhotoId(photoId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
    }

    public Post(int postId, int userId, int placeId, int photoId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content, int isPublic, String photoUrl) {
        setPostId(postId);
        setUserId(userId);
        setPlaceId(placeId);
        setPhotoId(photoId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
        setPhotoUrl(photoUrl);
    }

    // getter와 setter
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getSportCode() {
        return sportCode;
    }

    public void setSportCode(int sportCode) {
        this.sportCode = sportCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(int exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", placeId=" + placeId +
                ", photoId=" + photoId +
                ", sportCode=" + sportCode +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", exerciseDuration=" + exerciseDuration +
                ", content='" + content + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
