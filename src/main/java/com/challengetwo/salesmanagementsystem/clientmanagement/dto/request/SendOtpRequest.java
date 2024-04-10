package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class  SendOtpRequest {
    @NotBlank(message = "This field must not be empty")
    private @Email String email;
}
