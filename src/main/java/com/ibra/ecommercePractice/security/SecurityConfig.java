package com.ibra.ecommercePractice.security;

import com.ibra.ecommercePractice.security.jwt.JwtAuthFilter;
import com.ibra.ecommercePractice.security.jwt.JwtUtils;
import com.ibra.ecommercePractice.security.oauth2.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final StdOAuth2UserService stdOAuth2UserService;
    private final OidOAuth2UserService oidOAuth2UserService;
    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, StdOAuth2UserService stdOAuth2UserService, OAuth2LoginSuccessHandler oauth2LoginSuccessHandler, OidOAuth2UserService oidOAuth2UserService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.stdOAuth2UserService = stdOAuth2UserService;
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
        this.oidOAuth2UserService = oidOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtUtils jwtUtils) throws  Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/category/", "/product/**", "/order/**", "/pay/**", "/cancel/**", "/success/**", "/home/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(stdOAuth2UserService)
//                                .oidcUserService(oidOAuth2UserService) // Explicitly set for OIDC
//                        ).successHandler(oauth2LoginSuccessHandler)
//                );
        //                .formLogin(withDefaults())


        //failed test 1
//                .oauth2Login(oauth -> oauth
//                            .successHandler((request, response, authentication) -> {
//                    // Generate JWT after OAuth2 login
//                    String jwt = jwtUtils.generateToken((User) authentication);
//                    System.out.println(jwt + "...........................................");
//                    response.addHeader("Authorization", "Bearer " + jwt);
//                    response.sendRedirect("/product");
//                }));
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
