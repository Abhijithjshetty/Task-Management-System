package com.taskmanagement.mappers.api;


import com.sushikhacapitals.models.LoanUser;
import java.util.Optional;

public interface LoanUserDao {
    void insertUser(LoanUser user);

    Optional<LoanUser> findByUsername(String username);

    Optional<LoanUser> findByUserId(String userId);

    void updateLoanUser(LoanUser user);

    void updateUserOtp(String userId,String newOtp);

}
