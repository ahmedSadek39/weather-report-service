package com.app.weather.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsService implements UserDetails {

    private final String email;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsService(String email, String name, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getName() {
        return name;
    }
}
