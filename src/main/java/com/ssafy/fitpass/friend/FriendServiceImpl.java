package com.ssafy.fitpass.friend;

import com.ssafy.fitpass.exception.RegDBException;
import com.ssafy.fitpass.user.dto.RetUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;

    public FriendServiceImpl(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public boolean sendFriendRequest(int from, int to) {
        try {
            // from_user = from, to_user = toUser, status = 0인 행 개수 반환
            // select문에서 count로 해당되는 행 수 반환하도록 설정 -> 처음 테이블이 생성되어 값이 없을 경우(0) 대비
            int result = friendDao.selectOne(from, to);

            if (result == 1) {  // 친구 신청을 받은 상태라면
                return friendDao.updateRequestStatus(from, to) == 1;
            }
            // 친구 신청을 받지 않았다면 2개 행 삽입
            return friendDao.insertRequest(from, to) == 2;
        } catch (DataAccessException e) {
            throw new RegDBException("친구 요청을 처리하는 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("친구 요청을 처리하는 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public List<RetUser> getFriends(int userId) {
        try {
            // inner join 2번 쓰는 것으로 변경
            return friendDao.selectFriends(userId);
        } catch (DataAccessException e) {
            throw new RegDBException("친구 목록을 가져오는 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("친구 목록을 가져오는 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public List<RetUser> getFriendRequests(int userId) {
        try {
            return friendDao.selectFriendRequest(userId);
        } catch (DataAccessException e) {
            throw new RegDBException("친구 요청 목록을 가져오는 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("친구 요청 목록을 가져오는 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean deleteFriendRequest(int requestId, int currUserId) {
        try {
            return friendDao.deleteFriendRequest(requestId, currUserId) == 2;
        } catch (DataAccessException e) {
            throw new RegDBException("친구 요청 삭제 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("친구 요청을 삭제하는 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean deleteFriend(int from, int to) {
        try {
            return friendDao.deleteFriend(from, to) == 2;
        } catch (DataAccessException e) {
            throw new RegDBException("친구 관계 삭제 중 데이터 베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("친구 관계를 삭제하는 중 알 수 없는 오류가 발생했습니다.");
        }
    }
}
