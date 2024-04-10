package com.challengetwo.salesmanagementsystem.clientmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.AuthProvider;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import com.challengetwo.salesmanagementsystem.clientmanagement.repository.UserRepository;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.challengetwo.salesmanagementsystem.clientmanagement.model.Role.SUPER_ADMIN;
import static com.challengetwo.salesmanagementsystem.clientmanagement.model.Status.VERIFIED;


@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final UserRepository userRepository;

//    @EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    public void createSuperAdmin() {
        try {
            if (userRepository.findByRole(SUPER_ADMIN).isEmpty()) {
                User superAdmin = User.builder()
                        .email("pelumijsh@gmail.com")
                        .password(BCrypt.hashpw("KingPerry@29", BCrypt.gensalt()))
                        .lastName("Taiwo")
                        .authProvider(AuthProvider.LOCAL)
                        .firstName("Oluwapelumi").status(VERIFIED).role(SUPER_ADMIN).build();
                userRepository.save(superAdmin);
            }
        } catch (SalesManagementSystemException exception) {
            throw new SalesManagementSystemException(exception.getMessage());
        }
    }
}
