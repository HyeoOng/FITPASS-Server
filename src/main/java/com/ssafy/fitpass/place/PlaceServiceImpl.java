package com.ssafy.fitpass.place;

import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.exception.UserException;
import com.ssafy.fitpass.post.Post;
import org.springframework.dao.DataAccessException;
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
        try{
            if(userId <= 0){
                throw new UserException("사용자 정보가 잘못되었습니다.", "NI");
            }
            return placeDao.selectAll(userId);
        }catch (DataAccessException e){
            throw new RegDBException();
        }
    }

    @Override
    public List<Place> getMyPlaces(List<Post> posts) {
        try{
            List<Place> myPlaces = new ArrayList<>();
            posts.forEach(post -> {
                myPlaces.add(placeDao.selectByid(post.getPlaceId()));
            });
            return myPlaces;
        }catch (DataAccessException e){
            throw new RegDBException();
        }
    }

    @Override
    public String getPlaceName(int placeId) {
        try{
            if(placeId <= 0){
                throw new UserException("장소 정보가 잘못되었습니다.", "NI");
            }
            return placeDao.selectPlaceName(placeId);
        } catch (DataAccessException e){
            throw new RegDBException();
        }
    }


}
