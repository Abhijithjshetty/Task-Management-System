package com.taskmanagement.mappers.api;

import com.sushikhacapitals.models.SushikhaUser;

import java.util.List;
import java.util.Optional;

public interface SushikhaUserDao {
    void insert(SushikhaUser sushikhaUser);

    Optional<SushikhaUser> selectUserByEmail(String email);

    Optional<SushikhaUser> selectUserByPhone(String phone);

    Optional<SushikhaUser> getUserByRegistrationId(String sushikhaUserId);

    void update(SushikhaUser sushikhaUser);

    Optional<Byte[]> getProfilePicByUserId(String sushikhaUserId);

    Long selectTotalCount(String sushikhaUserId);

    List<SushikhaUser> selectRecordsInRange(String sushikhaUserId, Long calculatedOffset, Long pageSize);

    void updateUserStatus(SushikhaUser sushikhaUser);

    String selectUserIdByPhone(String phone);
}
