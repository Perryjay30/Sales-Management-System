package com.challengetwo.salesmanagementsystem.clientmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.request.*;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.LoginResponse;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;


public interface UserService {
    String register(UserRegistrationRequest userRegistrationRequest);

    Response createAccount(String email, VerifyOtpRequest verifyOtpRequest);

    void verifyOTP(VerifyOtpRequest verifyOtpRequest);

    String sendOTP(SendOtpRequest sendOtpRequest);

    LoginResponse login(LoginRequest loginRequest);
    Response deleteUser(Long id, DeleteRequest deleteRequest);

    Response updateCustomer(Long id, EditCustomerProfileRequest editCustomerProfileRequest);
    Response assignRoles(AssignRoleRequest assignRoleRequest) throws MessagingException;
    User getFoundCustomer(Optional<User> customerRepository, String message);
    User getExistingUser(String email);
    List<User> getAllUsers();
}
