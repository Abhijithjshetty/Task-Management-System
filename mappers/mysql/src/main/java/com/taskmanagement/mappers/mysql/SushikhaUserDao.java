package com.taskmanagement.mappers.mysql;

import com.sushikhacapitals.models.SushikhaUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface SushikhaUserDao extends com.taskmanagement.mappers.api.SushikhaUserDao {
    void insert(@Param("sushikhaUser") SushikhaUser sushikhaUser);

    Optional<SushikhaUser> selectUserByEmail(@Param("email") String email);

    Optional<SushikhaUser> selectUserByPhone(@Param("phone") String phone);

    Optional<SushikhaUser> getUserByRegistrationId(@Param("sushikhaUserId") String sushikhaUserId);

    void update(@Param("sushikhaUser") SushikhaUser sushikhaUser);

    Optional<Byte[]> getProfilePicByUserId(@Param("sushikhaUserId") String sushikhaUserId);

    Long selectTotalCount(@Param("sushikhaUserId") String sushikhaUserId);

    List<SushikhaUser> selectRecordsInRange(@Param("sushikhaUserId") String sushikhaUserId,
                                            @Param("calculatedOffset") Long calculatedOffset,
                                            @Param("pageSize") Long pageSize);

    void updateUserStatus(@Param("sushikhaUser") SushikhaUser sushikhaUser);

    String selectUserIdByPhone(@Param("phone") String phone);


}
