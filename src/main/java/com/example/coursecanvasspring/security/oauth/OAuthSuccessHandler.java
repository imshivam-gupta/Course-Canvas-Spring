package com.example.coursecanvasspring.security.oauth;

import com.example.coursecanvasspring.entity.user.Student;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.AuthProvider;
import com.example.coursecanvasspring.helper.SecurityFunctions;
import com.example.coursecanvasspring.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.example.coursecanvasspring.constants.StringConstants.*;


@Slf4j
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

        User user = new Student();
        user.setEmailVerified(true);
        user.setPassword(SecurityFunctions.passwordEncoder(OAUTH_DUMMY_PASSWORD));

        if(authorizedClientRegistrationId.equalsIgnoreCase(AuthProvider.GOOGLE.getProvider())){
            user.setEmail(Objects.requireNonNull(oauth2User.getAttribute(GOOGLE_OAUTH_EMAIL)).toString());
            user.setProfilePicture(Objects.requireNonNull(oauth2User.getAttribute(GOOGLE_OAUTH_PROFILEPIC)).toString());
            user.setName(Objects.requireNonNull(oauth2User.getAttribute(GOOGLE_OAUTH_NAME)).toString());
            user.setProviderId(oauth2User.getName());
            user.setOauthProvider(AuthProvider.GOOGLE.getProvider());
        } else{
            log.error("Unknown provider");
        }

        User existingUser = userService.findUserByEmail(user.getEmail()).orElse(null);
        if(existingUser == null){
            userService.saveUser(user);
        }

        response.sendRedirect("/");
    }
}
