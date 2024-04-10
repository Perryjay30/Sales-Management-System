package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteRequest {
    @NotBlank(message = "This field must not be empty")
    private String password;
}
