package com.example.demo.security;

import com.example.demo.model.Guest;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Guest guest;

    public CustomUserDetails(Guest guest) {
        this.guest = guest;
    }

    public Long getId() {
        return guest.getId();
    }

    public String getRole() {
        return guest.getRole();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> guest.getRole());
    }

    @Override public String getPassword() { return "Rohes"; }
    @Override public String getUsername() { return "Rohes123"; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
