package com.ibra.ecommercePractice.service.interf;

import com.ibra.ecommercePractice.dto.AddressDto;
import com.ibra.ecommercePractice.dto.Response;

public interface AddressService {
    public Response saveAndUpdateAddress(AddressDto addressDto);
}
