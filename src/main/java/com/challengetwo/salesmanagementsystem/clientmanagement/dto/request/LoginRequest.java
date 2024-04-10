package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
   @NotBlank(message = "This field is required")
   private String email;
   @NotBlank(message = "This field is required")
   private String password;
}
