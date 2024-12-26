package com.ibra.ecommercePractice.service.impl;

import com.ibra.ecommercePractice.dto.LoginRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.dto.UserDto;
import com.ibra.ecommercePractice.enums.UserRole;
import com.ibra.ecommercePractice.exception.InvalidCredentialsException;
import com.ibra.ecommercePractice.exception.NotFoundException;
import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.UserRepository;
import com.ibra.ecommercePractice.security.jwt.JwtUtils;
import com.ibra.ecommercePractice.service.interf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, EntityDtoMapper entityDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.entityDtoMapper = entityDtoMapper;
    }


    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role;
        if (registrationRequest.getRole() != null && registrationRequest.getRole().name().equalsIgnoreCase("ADMIN")){
            role = UserRole.ADMIN;
        } else {
            role = UserRole.USER;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        UserDto userDto = entityDtoMapper.mapUserToUserDto(savedUser);
        return Response.builder()
                .status(200)
                .message("User created successfully")
                .userDto(userDto)
                .build();
    }


    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }

        String token = jwtUtils.generateToken(user);

        return Response.builder()
                .status(200)
                .token(token)
                .expirationTime("6 Months")
                .role(user.getRole().name())
                .message("User logged in successfully")
                .build();
    }

    @Override
    public Response getAllUsers() {

        List<User> users = userRepository.findAll();

        System.out.println(users);

        List<UserDto> userDtos = users.stream()
                .map(entityDtoMapper::mapUserToUserDto)
                .toList();

        return Response.builder()
                .status(200)
                .userDtoList(userDtos)
                .build();
    }


    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User email is: {}", email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();

        UserDto userDto = entityDtoMapper.mapUsertoUserDtoPlusAddressAndOrderHistory(user);
        return Response.builder()
                .status(200)
                .userDto(userDto)
                .build();
    }
}
