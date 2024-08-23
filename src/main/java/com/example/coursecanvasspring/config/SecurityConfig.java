package com.example.coursecanvasspring.config;

import com.example.coursecanvasspring.helper.StringConstants;
import com.example.coursecanvasspring.security.JWTFilter;
import com.example.coursecanvasspring.security.OAuthFailureHandler;
import com.example.coursecanvasspring.security.OAuthSuccessHandler;
import com.example.coursecanvasspring.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailService;

    private final OAuthSuccessHandler successHandler;

    private final OAuthFailureHandler authFailureHandler;

    private final JWTFilter jwtFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailService, OAuthSuccessHandler successHandler, OAuthFailureHandler authFailureHandler, JWTFilter jwtFilter) {
        this.userDetailService = userDetailService;
        this.successHandler = successHandler;
        this.authFailureHandler = authFailureHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(StringConstants.PERMITTED_ROUTES).permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.oauth2Login(oauth -> {
            oauth.successHandler(successHandler);
            oauth.failureHandler(authFailureHandler);
        });

        return httpSecurity.build();
    }
}
