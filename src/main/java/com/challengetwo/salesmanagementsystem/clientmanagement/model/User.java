package com.challengetwo.salesmanagementsystem.clientmanagement.model;

import com.challengetwo.salesmanagementsystem.clientmanagement.otptoken.OtpToken;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
//    @NotBlank(message = "This field is required")
    private String firstName;
//    @NotBlank(message = "This field is required")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Status status;
//    @NotBlank(message = "This field is required")
//    @Email(message = "This email must be valid")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    private String homeAddress;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CustomerOrder> customerOrderList = new ArrayList<>();
    private double totalSpending;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "otp_token_id", referencedColumnName = "id", nullable = true)
    private OtpToken otpToken;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Cart> customerCart = new ArrayList<>();
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> storeAddress = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();
}
