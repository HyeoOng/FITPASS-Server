package com.ssafy.fitpass.post;

import com.ssafy.fitpass.photo.Photo;
import com.ssafy.fitpass.place.Place;

public class PostRequestDTO {
    private Post post;
    private Photo photo;
    private Place place;

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public Photo getPhoto() {
        return photo;
    }

    public Place getPlace() {
        return place;
    }
}
