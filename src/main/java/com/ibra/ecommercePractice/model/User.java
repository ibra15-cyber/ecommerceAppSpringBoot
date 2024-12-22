package com.ibra.ecommercePractice.model;

import com.ibra.ecommercePractice.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "name is required")
//    private String name;
//
//    @Column(unique = true)
//    @NotBlank(message = "Email is required")
//    private String email;
//
//    @Column(name="Password")
//    @NotBlank(message = "Password must be entered")
//    private String password;
//
//    @Column(name="phone_number")
//    @NotBlank(message = "Phone number must be entered")
//    private String phoneNumber;
//
////    @Enumerated
//    private UserRole role;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<OrderItem> orderItemList;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Address address;
//
//    private final LocalDateTime createdAt = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name="Password")
    @NotBlank(message = "Password must be entered")
    private String password;

    @Column(name="phone_number")
    @NotBlank(message = "Phone number must be entered")
    private String phoneNumber;

    //    @Enumerated
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    private final LocalDateTime createdAt = LocalDateTime.now();

}
