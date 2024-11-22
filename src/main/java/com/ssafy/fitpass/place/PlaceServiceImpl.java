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
    public String getPlaceName(int placeId) {
        return placeDao.selectPlaceName(placeId);
    }


}
