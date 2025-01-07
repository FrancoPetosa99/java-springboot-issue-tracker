package com.issue_tracker.issue_tracker.config;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String role;
    private Integer id;

    public CustomUserDetails(
        String userName,
        String name,
        String lastName,
        String email, 
        String role, 
        Integer id
    ) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }
    
    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Integer getUserId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}