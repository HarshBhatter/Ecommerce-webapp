package com.example.ecomMyself.ecomMyself.configuration;

import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Roles_repo;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.example.ecomMyself.ecomMyself.service.JwtService;
import com.example.ecomMyself.ecomMyself.service.User_service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private User_service user_service;
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private Roles_repo roles_repo;
    @Autowired
    private JwtService jwtService;
    @Value("${frontend.url}")
    private String fronendUrl;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            email = oauthUser.getAttribute("login"); // fallback for providers like GitHub
        }
        Users user;
        if (!user_repo.existsByUsername(email)) {
            user=user_service.saveOAuthUser(new Users(email));
        } else {
            user = user_repo.findByUsername(email);
        }

        String token = jwtService.generateToken(user.getUsername(), user.getVersion());

//        response.setContentType("application/json");
//        response.getWriter().write("{ \"token\": \"Bearer " + token + "\" }");
//        response.setContentType("text/plain"); //as custom is also sending plain data
//        response.getWriter().write(token); // no "Bearer " prefix
        response.sendRedirect(fronendUrl+"login/Oauth?token=" + token);

    }
}
