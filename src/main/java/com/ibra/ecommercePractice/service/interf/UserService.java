package com.ibra.ecommercePractice.service.interf;

import com.ibra.ecommercePractice.dto.LoginRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.dto.UserDto;
import com.ibra.ecommercePractice.model.User;
import org.springframework.stereotype.Component;


public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();


}
