package com.ssafy.fitpass.post;

import com.ssafy.fitpass.exception.InputException;

import java.util.Date;

public class Post {
    private int postId;
    private int userId;
    private int placeId;
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

    public Post(int userId, int placeId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content) {
        setUserId(userId);
        setPlaceId(placeId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
    }


    public Post(int postId, int userId, int placeId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content, int isPublic) {
        setPostId(postId);
        setUserId(userId);
        setPlaceId(placeId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
    }

    public Post(int postId, int userId, int placeId, int sportCode, String title, Date createdAt, Date updatedAt, int exerciseDuration, String content, int isPublic, String photoUrl) {
        setPostId(postId);
        setUserId(userId);
        setPlaceId(placeId);
        setSportCode(sportCode);
        setTitle(title);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
        setPhotoUrl(photoUrl);
    }

    public Post(int userId, int placeId, int sportCode, String title, int exerciseDuration, String content, int isPublic) {
        setUserId(userId);
        setPlaceId(placeId);
        setSportCode(sportCode);
        setTitle(title);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
    }

    public Post(int placeId, int sportCode, String title, int exerciseDuration, String content, int isPublic) {
        setPlaceId(placeId);
        setSportCode(sportCode);
        setTitle(title);
        setExerciseDuration(exerciseDuration);
        setContent(content);
        setIsPublic(isPublic);
    }

    // getter와 setter
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        if (postId <= 0) {
            throw new InputException("postId는 0보다 큰 값이어야 합니다.");
        }
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new InputException("userId는 0보다 큰 값이어야 합니다.");
        }
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        if (placeId <= 0) {
            throw new InputException("placeId는 0보다 큰 값이어야 합니다.");
        }
        this.placeId = placeId;
    }

    public int getSportCode() {
        return sportCode;
    }

    public void setSportCode(int sportCode) {
        if (sportCode <= 0) {
            throw new InputException("sportCode는 0보다 큰 값이어야 합니다.");
        }
        this.sportCode = sportCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InputException("제목은 비어 있을 수 없습니다.");
        }
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
        if(exerciseDuration < 0 || exerciseDuration % 30 != 0) {
            throw new InputException("운동 시간은 0보다 작을 수 없고, 30분 단위로 입력해야 합니다.");
        }

        this.exerciseDuration = exerciseDuration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.isBlank()) {
            throw new InputException("내용은 비어 있을 수 없습니다.");
        }
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
        this.isPublic = (isPublic < 0 || isPublic > 2) ? 0 : isPublic;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", placeId=" + placeId +
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
