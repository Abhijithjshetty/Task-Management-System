package com.sushikhacapitals.core.security.models;

import com.sushikhacapitals.models.LoanUser;
import com.sushikhacapitals.models.enums.UserRole;
import com.sushikhacapitals.models.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanUserAuth implements UserDetails {
    private String username;
    @JsonIgnore
    private String otp;
    private String userId;
    private UserStatus status;
    private UserRole role;
    private String token;

    // Constructor to initialize from a LoanUser object
    public LoanUserAuth(LoanUser loanUser) {
        this.username = loanUser.getUsername();
        this.otp = loanUser.getOtp();
        this.status = loanUser.getStatus();
        this.role = loanUser.getUserRole();
        this.userId = loanUser.getUserId();
    }

    // Default constructor
    public LoanUserAuth() {}

    // Get authorities based on the role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    // Getter for OTP without overriding UserDetails methods
    public String getOtp() {
        return otp;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return otp; // If OTP is used as password, provide it here
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == null || status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == null || status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == null || status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status == null || status.equals(UserStatus.ACTIVE);
    }
}
