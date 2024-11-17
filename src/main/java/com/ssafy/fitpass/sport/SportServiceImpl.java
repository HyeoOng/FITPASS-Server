package com.ssafy.fitpass.sport;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportServiceImpl implements SportService {

    private final SportDao sportDao;

    public SportServiceImpl(SportDao sportDao) {
        this.sportDao = sportDao;
    }

    @Override
    public boolean createSport(Sport sport) {
        return sportDao.insertSport(sport) == 1;
    }

    @Override
    public boolean modifySport(Sport sport) {
        return sportDao.updateSport(sport) == 1;
    }

    @Override
    public List<Sport> getAllSports() {
        return sportDao.selectAll();
    }

    @Override
    public boolean removeSport(int sportCode) {
        return sportDao.deleteSport(sportCode) == 1;
    }
}
