package com.challengetwo.salesmanagementsystem.reporting.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String homeAddress;
    private Double totalSpending;
}
