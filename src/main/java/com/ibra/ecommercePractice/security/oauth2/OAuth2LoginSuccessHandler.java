package com.ibra.ecommercePractice.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.UserRepository;
import com.ibra.ecommercePractice.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
//
//        AuthUser authUser = (AuthUser) authentication.getPrincipal();
//
//        String token = jwtUtils.generateToken(authUser.getUser());
//
//        // Create response body
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("token", token);
//
//        // Write response
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(response.getWriter(), responseBody);

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String token = jwtUtils.generateToken(user);

        response.addHeader("Authorization", "Bearer " + token);

        System.out.println(token);
        response.sendRedirect("/"); // Redirect to the home page or wherever you want
    }
}