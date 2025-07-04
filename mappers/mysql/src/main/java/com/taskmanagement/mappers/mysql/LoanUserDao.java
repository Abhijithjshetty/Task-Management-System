package com.taskmanagement.mappers.mysql;

import com.sushikhacapitals.models.LoanUser;
import org.apache.ibatis.annotations.Param;
import java.util.Optional;

public interface LoanUserDao extends com.taskmanagement.mappers.api.LoanUserDao {
    void insertUser(@Param("user") LoanUser user);

    Optional<LoanUser> findByUsername(@Param("username") String username);

    Optional<LoanUser> findByUserId(String userId);

    void updateLoanUser(@Param("user") LoanUser user);

    void updateUserOtp(@Param("userId") String userId,@Param("newOtp") String newOtp);


}