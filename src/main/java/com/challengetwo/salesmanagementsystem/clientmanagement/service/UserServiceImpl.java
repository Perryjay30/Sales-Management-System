package com.challengetwo.salesmanagementsystem.clientmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.request.*;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.LoginResponse;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.AuthProvider;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.Role;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.Status;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import com.challengetwo.salesmanagementsystem.clientmanagement.otptoken.OtpToken;
import com.challengetwo.salesmanagementsystem.clientmanagement.otptoken.OtpTokenRepository;
import com.challengetwo.salesmanagementsystem.clientmanagement.otptoken.Token;
import com.challengetwo.salesmanagementsystem.clientmanagement.repository.UserRepository;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.notification.EmailService;
import com.challengetwo.salesmanagementsystem.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;



@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String register(UserRegistrationRequest registrationRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (this.userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email Address already exists!!");
        } else {
            User user = (User)objectMapper.convertValue(registrationRequest, User.class);
            user.setRole(Role.USER);
            user.setStatus(Status.UNVERIFIED);
            user.setAuthProvider(AuthProvider.LOCAL);
            this.userRepository.save(user);
            SendOtpRequest sendOtpRequest = new SendOtpRequest();
            sendOtpRequest.setEmail(registrationRequest.getEmail());
            return this.sendOTP(sendOtpRequest);
        }
    }

    public String sendOTP(SendOtpRequest sendOtpRequest) {
        User existingUser = this.getExistingUser(sendOtpRequest.getEmail());
        return this.generateOtpToken(sendOtpRequest, existingUser);
    }

    private String generateOtpToken(SendOtpRequest sendOtpRequest, User existingUser) {
        String token = Token.generateToken(4);
        OtpToken otpToken = new OtpToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10L), existingUser);
        this.otpTokenRepository.save(otpToken);
        this.emailService.send(sendOtpRequest.getEmail(), this.emailService.buildEmail(existingUser.getFirstName(), token));
        return "Token successfully sent to your email. Please check.";
    }

    public Response createAccount(String email, VerifyOtpRequest verifyOtpRequest) {
        verifyOTP(verifyOtpRequest);
        User existingUser = this.getExistingUser(email);
        this.userRepository.enableUser(Status.VERIFIED, existingUser.getEmail());
//        existingUser.setOtpToken(verifyOtpRequest.);
        return new Response("User registration successful");
    }

    @Override
    public User getExistingUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            return new SalesManagementSystemException("User not found!!");
        });
    }

    public void verifyOTP(VerifyOtpRequest verifyOtpRequest) {
        OtpToken foundToken = (OtpToken)this.otpTokenRepository.findByToken(verifyOtpRequest.getToken()).orElseThrow(() -> {
            return new RuntimeException("Token doesn't exist");
        });
        if (foundToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        } else if (foundToken.getVerifiedAt() != null) {
            throw new RuntimeException("Token has been used");
        } else if (!Objects.equals(verifyOtpRequest.getToken(), foundToken.getToken())) {
            throw new RuntimeException("Incorrect token");
        } else {
            this.otpTokenRepository.setVerifiedAt(LocalDateTime.now(), verifyOtpRequest.getToken());
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User foundUser = this.getExistingUser(loginRequest.getEmail());
        if (foundUser.getStatus() != Status.VERIFIED) {
            throw new RuntimeException("User is not verified");
        } else {
            Authentication authenticating = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            if (authenticating.isAuthenticated()) {
                String token = this.jwtService.generateToken(loginRequest.getEmail());
                String loginMessage = "Login Successful!!";
                return new LoginResponse(loginMessage, token);
            } else {
                throw new UsernameNotFoundException("Invalid credentials!!");
            }
        }
    }

    @Override
    public Response deleteUser(Long id, DeleteRequest deleteRequest) {
        User foundUser = getFoundCustomer(userRepository.findById(id), "User doesn't exist");
        String randomToken = UUID.randomUUID().toString();
        String encoded = BCrypt.hashpw(randomToken, BCrypt.gensalt());
//        if (BCrypt.checkpw(deleteRequest.getPassword(), foundUser.getPassword())) {
//            String deleteCustomer = "Deleted" + " " + foundUser.getEmail() + " " + encoded;
//            foundUser.setEmail(deleteCustomer);
//            userRepository.save(foundUser);
//            return new Response("User deleted successfully");
//        } else {
//            throw new RuntimeException("Customer can't be deleted");
//        }
        if(BCrypt.checkpw(deleteRequest.getPassword(), foundUser.getPassword())) {
            userRepository.delete(foundUser);
            return new Response("User deleted successfully");
        } else {
            throw new RuntimeException("Incorrect password, Kindly make sure you providing" +
                    " your correct password!!");
        }
    }

    @Override
    public User getFoundCustomer(Optional<User> userRepository, String message) {
        return userRepository.orElseThrow(
                () -> new RuntimeException(message));
    }

    @Override
    public Response updateCustomer(Long id, EditCustomerProfileRequest editCustomerProfileRequest) {
        var customer = userRepository.findById(id);
        if (customer.isEmpty()) throw new RuntimeException("user not found");
        User foundUser = updatingTheCustomer(editCustomerProfileRequest, customer);
        userRepository.save(foundUser);
        return new Response("User profile updated successfully");
    }

    @Override
    public Response assignRoles(AssignRoleRequest assignRoleRequest) throws MessagingException {
        User existingUser = userRepository.findByEmail(assignRoleRequest.getEmail())
                .orElseThrow(() -> new SalesManagementSystemException("User isn't available"));
        existingUser.setRole(Role.valueOf(assignRoleRequest.getUserRole().toUpperCase()));
        userRepository.save(existingUser);
//        emailService.emailForAssignRole(existingUser.getEmail(), existingUser.getFirstName());
        return new Response(existingUser.getFirstName() + " is now a  " +
                " " + existingUser.getRole());
    }

    private User updatingTheCustomer(EditCustomerProfileRequest editCustomerProfileRequest, Optional<User> customer) {
        var foundCustomer = customer.get();
        foundCustomer.setFirstName(editCustomerProfileRequest.getFirstName() != null && !editCustomerProfileRequest.getFirstName()
                .equals("") ? editCustomerProfileRequest.getFirstName() : foundCustomer.getFirstName());
//        if(userRepository.findByPhoneNumber(editCustomerProfileRequest.getPhone()).isPresent())
//            throw new RuntimeException("This Phone Number has been taken, kindly register with another");
//        else
        foundCustomer.setPhoneNumber(editCustomerProfileRequest.getPhone()!= null && !editCustomerProfileRequest.getPhone()
                .equals("") ? editCustomerProfileRequest.getPhone() : foundCustomer.getPhoneNumber());
        return stillUpdatingCustomer(editCustomerProfileRequest, foundCustomer);
    }

    private User stillUpdatingCustomer(EditCustomerProfileRequest editCustomerProfileRequest, User foundUser) {
        foundUser.setLastName(editCustomerProfileRequest.getLastName() != null && !editCustomerProfileRequest.getLastName()
                .equals("") ? editCustomerProfileRequest.getLastName() : foundUser.getLastName());
        foundUser.setHomeAddress(editCustomerProfileRequest.getHomeAddress() != null && !editCustomerProfileRequest.getHomeAddress()
                .equals("") ? editCustomerProfileRequest.getHomeAddress() : foundUser.getHomeAddress());
        foundUser.setEmail(editCustomerProfileRequest.getEmail() != null && !editCustomerProfileRequest.getEmail()
                .equals("") ? editCustomerProfileRequest.getEmail() : foundUser.getEmail());
        return foundUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
