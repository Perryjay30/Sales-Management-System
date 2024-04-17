package com.challengetwo.salesmanagementsystem.clientmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.request.*;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.LoginResponse;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testThatUserCanRegister() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("starlight@gmail.com");
        userRegistrationRequest.setFirstName("AppGlobal");
        userRegistrationRequest.setLastName("Perry");
        userRegistrationRequest.setPassword("Perryjay@17");
        String response = userService.register(userRegistrationRequest);
        assertEquals("Token successfully sent to your email. Please check.", response);
    }

    @Test
    void testThatUserCanCreateAccount() {
        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.setToken("1336");
        Response response = userService.createAccount("starlight@gmail.com", verifyOtpRequest);
        assertEquals("User registration successful", response.getMessage());
    }

    @Test
    void testThatUserCanLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("starlight@gmail.com");
        loginRequest.setPassword("Perryjay@17");
        LoginResponse response = userService.login(loginRequest);
        assertEquals("Login successful!!", response.getMessage());
    }

    @Test
    void testThatUserCanUpdateProfile() {
        EditCustomerProfileRequest editCustomerProfileRequest = new EditCustomerProfileRequest();
        editCustomerProfileRequest.setEmail("mrjesus3003@gmail.com");
        editCustomerProfileRequest.setPhone("0812345678");
        editCustomerProfileRequest.setLastName("Montreal");
        editCustomerProfileRequest.setFirstName("Martinelli");
        Response response = userService.updateCustomer(2L, editCustomerProfileRequest);
        assertEquals("User profile updated successfully", response.getMessage());
    }

    @Test
    void testThatUserCanBeDeleted() {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setPassword("Perryjay@17");
        Response response = userService.deleteUser(2L, deleteRequest);
        assertEquals("User deleted successfully", response.getMessage());
    }

}