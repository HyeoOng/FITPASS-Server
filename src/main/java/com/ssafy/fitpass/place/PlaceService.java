package com.ssafy.fitpass.place;

import com.ssafy.fitpass.post.Post;

import java.util.List;

public interface PlaceService {
    List<Place> getAllPlaces(int userId);

    List<Place> getMyPlaces(List<Post> posts);

    String getPlaceName(int placeId);
}
