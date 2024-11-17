package com.ssafy.fitpass.sport;

import java.util.List;

public interface SportService {
    boolean createSport(Sport sport);
    boolean modifySport(Sport sport);
    List<Sport> getAllSports();
    boolean removeSport(int sportCode);
}
