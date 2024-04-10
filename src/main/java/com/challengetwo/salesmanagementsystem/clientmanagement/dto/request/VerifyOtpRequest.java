package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {
    @NotBlank(message = "This field must not be empty")
    private String token;
//    @NotBlank(message = "This field must not be empty")
//    private String email;
}
