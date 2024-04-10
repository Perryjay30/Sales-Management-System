package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import lombok.Data;

@Data
public class EditCustomerProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String homeAddress;
}
