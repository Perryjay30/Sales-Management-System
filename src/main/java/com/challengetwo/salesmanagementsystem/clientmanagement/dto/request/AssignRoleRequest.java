package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

//import com.starlightonlinestore.data.models.Role;
import lombok.Data;

@Data
public class AssignRoleRequest {
    private String email;
    private String userRole;
}
