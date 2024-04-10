package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Data
public class UserRegistrationRequest {
    @NotBlank(message = "This field is required")
    private String email;
    @NotBlank(message = "This field is required")
    private String password;
    @NotBlank(message = "This field is required")
    private String firstName;
    @NotBlank(message = "This field is required")
    private String lastName;

    public String getPassword() {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
