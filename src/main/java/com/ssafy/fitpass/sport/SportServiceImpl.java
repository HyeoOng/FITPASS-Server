package com.ssafy.fitpass.sport;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            return sportDao.insertSport(sport) == 1;
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("이미 존재하는 스포츠 코드입니다.");
        } catch (DataAccessException e) {
            throw new RuntimeException("스포츠 생성 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean modifySport(Sport sport) {
        try {
            int rowsAffected = sportDao.updateSport(sport);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("수정하려는 스포츠 코드가 존재하지 않습니다.");
            }
            return rowsAffected == 1;
        } catch (DataAccessException e) {
            throw new RuntimeException("스포츠 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public List<Sport> getAllSports() {
        try {
            return sportDao.selectAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("스포츠 목록 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean removeSport(int sportCode) {
        try {
            int rowsAffected = sportDao.deleteSport(sportCode);
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("삭제하려는 스포츠 코드가 존재하지 않습니다.");
            }
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("스포츠 코드가 다른 데이터에서 참조 중입니다. 삭제할 수 없습니다.");
        } catch (DataAccessException e) {
            throw new RuntimeException("스포츠 삭제 중 오류가 발생했습니다.");
        }
    }
}
