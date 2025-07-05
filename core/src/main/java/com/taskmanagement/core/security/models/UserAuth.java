package com.taskmanagement.core.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.taskmanagement.models.User;
import com.taskmanagement.models.enums.UserRole;
import com.taskmanagement.models.enums.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuth implements UserDetails {
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String userId;
    private UserStatus status;
    //todo : Update with upcoming build for authorities
    private UserRole role;
    private String token;

    public UserAuth(User user){
        this.username =user.getUsername();
        this.password=user.getPassword();
        this.status=user.getStatus();
        this.role=user.getUserRole();
        this.userId=user.getUserId();
    }
    public UserAuth(){}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status!=null?status.equals(UserStatus.ACTIVE):true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status!=null?status.equals(UserStatus.ACTIVE):true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != null ? status.equals(UserStatus.ACTIVE) : true;
    }

    @Override
    public boolean isEnabled() {
        return status != null ? status.equals(UserStatus.ACTIVE) : true;
    }
}
