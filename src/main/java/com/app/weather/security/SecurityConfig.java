package com.app.weather.security;

import com.app.weather.utility.AppConstant;
import com.app.weather.security.OAuth2SuccessHandler;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Component
public class SecurityConfig {

    private final OAuth2SuccessHandler successHandler;
    public SecurityConfig(OAuth2SuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(AppConstant.AUTH_WHITE_LIST).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2.successHandler(successHandler).defaultSuccessUrl("/"));

        return http.build();
    }

}
