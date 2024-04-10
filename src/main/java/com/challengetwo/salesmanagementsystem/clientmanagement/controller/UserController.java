package com.challengetwo.salesmanagementsystem.clientmanagement.controller;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.request.*;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import com.challengetwo.salesmanagementsystem.clientmanagement.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/salesManagementSystem/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationRequest
                                              userRegistrationRequest) {
        return ResponseEntity.ok(userService.register(userRegistrationRequest));
    }

    @PostMapping("/createAccount/{email}")
    public ResponseEntity<?> createAccount(@PathVariable String email, @Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAccount(email, verifyOtpRequest));
    }


    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<?> getExistingUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.getExistingUser(email));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id, @Valid @RequestBody DeleteRequest deleteRequest) {
        log.info("Id -> {}", id);
      return ResponseEntity.ok(userService.deleteUser(id, deleteRequest));
    }

    @PatchMapping("/updateProfile/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody EditCustomerProfileRequest editCustomerProfileRequest) {
       return ResponseEntity.ok(userService.updateCustomer(id, editCustomerProfileRequest));
    }

    @PostMapping("/assignRoles")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> assignRoles(@RequestBody AssignRoleRequest assignRoleRequest) throws MessagingException {
        return ResponseEntity.ok(userService.assignRoles(assignRoleRequest));
    }

    @GetMapping("getAllUsers")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public List<User> getAllUsers() {
       return userService.getAllUsers();
    }



}
