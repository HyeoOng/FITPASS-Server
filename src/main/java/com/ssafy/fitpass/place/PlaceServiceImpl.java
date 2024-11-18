package com.ssafy.fitpass.place;

import org.springframework.stereotype.Service;

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
    public Place getPlace(int postId) {
        return placeDao.selectOne(postId);
    }

    @Override
    public int getPlaceId(int kakaoMapId) {
        return placeDao.selectOneByKakaoMapId(kakaoMapId);
    }

    @Override
    public boolean createPlace(Place place) {
        return placeDao.insertPlace(place)==1;
    }
}
