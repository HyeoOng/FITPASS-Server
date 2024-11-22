package com.ssafy.fitpass.place;

import java.util.List;

public interface PlaceService {
    List<Place> getAllPlaces(int userId);

    String getPlaceName(int placeId);
}
