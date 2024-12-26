package com.ibra.ecommercePractice.security.oauth2;

import com.ibra.ecommercePractice.enums.UserRole;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OidOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = new DefaultOidcUser(
                AuthorityUtils.createAuthorityList("ROLE_USER"),
                userRequest.getIdToken());

        String email = oidcUser.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(email, oidcUser.getFullName(), oidcUser.getPhoneNumber()));

        return oidcUser;
    }

    private User registerNewUser(String email, String name, String phone) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        if (phone != null) {
            newUser.setPhoneNumber(phone);
        } else newUser.setPhoneNumber("024******");
        newUser.setRole(UserRole.USER);
        return userRepository.save(newUser);
    }
}