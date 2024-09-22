package com.app.weather.security;

import com.app.weather.repos.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User processOAuthPostLogin(OAuth2User oAuth2User, String provider) {

        String email = oAuth2User.getAttribute("login");
        String name = oAuth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            existingUser = Optional.of(new User(email, name, provider));
        } else {
            existingUser.get().setName(name);
        }

        UserDetailsService userDetailsService = new UserDetailsService(
                existingUser.get().getEmail(),
                existingUser.get().getName(),
                new ArrayList<>()); // no authorities added yet

        SecurityContextHolder.getContext().
                setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsService, null, new ArrayList<>()));

        return userRepository.save(existingUser.get());
    }

    Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
