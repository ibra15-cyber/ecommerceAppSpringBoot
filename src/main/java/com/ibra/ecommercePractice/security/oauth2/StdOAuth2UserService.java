package com.ibra.ecommercePractice.security.oauth2;

import com.ibra.ecommercePractice.enums.UserRole;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.UserRepository;
import com.ibra.ecommercePractice.security.AuthUser;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StdOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public StdOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);


        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("open oauth2 token ........................"+ accessToken);

        // Extract email from OAuth2 user whihc is null for Standard openAuth2
        String email = oauth2User.getAttribute("email");

        // Find or create user but it wont work since the email isn't given above
        //also the second part will not work either since most of the fields we are trying to extract are empty
        //if i was the one imlementing this, i will check if user exist then will scrap attributes from oauth2User to the user
        //then save User to the db with userRepository
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createUser(oauth2User));

//        return (OAuth2User) new AuthUser(user);
        return oauth2User;
    }

    private User createUser(OAuth2User oauth2User) {
        User user = new User();
        user.setEmail(oauth2User.getAttribute("email"));
//        System.out.println(user.getEmail());
        user.setName(oauth2User.getAttribute("name"));
        // Set a random password or handle as needed
        user.setPassword(UUID.randomUUID().toString());
        user.setRole(UserRole.USER);

        System.out.println("user.............................................." + user);
        return userRepository.save(user);
    }
}
