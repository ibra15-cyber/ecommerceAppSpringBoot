package com.ibra.ecommercePractice.repository;

import com.ibra.ecommercePractice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
