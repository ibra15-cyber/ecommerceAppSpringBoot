package com.ibra.ecommercePractice.service.impl;

import com.ibra.ecommercePractice.dto.AddressDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
import com.ibra.ecommercePractice.model.Address;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.repository.AddressRepository;
import com.ibra.ecommercePractice.service.interf.AddressService;
import com.ibra.ecommercePractice.service.interf.UserService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService {

    private final AddressRepository addressRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final UserService userService;

    public AddressServiceImp(UserService userService, AddressRepository addressRepository, EntityDtoMapper entityDtoMapper) {
        this.userService = userService;
        this.addressRepository = addressRepository;
        this.entityDtoMapper = entityDtoMapper;
    }


    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();

//        System.out.println(user);
//        System.out.println(user.getAddress());
        Address address = user.getAddress(); //We got the address if he has one already
        //if the user doesn't have an address, create one and assign to the user
        if (address == null) {
            address = new Address();
            address.setUser(user);
        }

        //if address is not null aka it exist, update it
        //we could remove the if statements
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getState() != null) address.setState(addressDto.getState());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepository.save(address);

        String message = (user.getAddress() != null) ? "Address successfully added": "Address successfully updated";

        return Response.builder()
                .status(200)
                .addressDto(addressDto)
                .userDto(entityDtoMapper.mapUserToUserDto(user))
                .message(message)
                .build();
    }
}
