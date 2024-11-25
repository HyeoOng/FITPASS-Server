package com.ssafy.fitpass.place;

import com.ssafy.fitpass.post.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceDao placeDao;

    public PlaceServiceImpl(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public List<Place> getAllPlaces(int userId) {
        return placeDao.selectAll(userId);
    }

    @Override
    public List<Place> getMyPlaces(List<Post> posts) {
        List<Place> myPlaces = new ArrayList<>();

        posts.forEach(post -> {
            myPlaces.add(placeDao.selectByid(post.getPlaceId()));
        });
        return myPlaces;
    }

    @Override
    public String getPlaceName(int placeId) {
        return placeDao.selectPlaceName(placeId);
    }


}
