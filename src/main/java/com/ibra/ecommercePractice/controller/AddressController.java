package com.ibra.ecommercePractice.controller;

import com.ibra.ecommercePractice.dto.AddressDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.service.interf.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.saveAndUpdateAddress(addressDto));
    }
}
