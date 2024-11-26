package com.example.amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "username is empty")
    @Size(min = 5, message = "username must be at least 5 letters long")
    private String username;

    @NotEmpty(message = "password is empty")
    @Pattern(regexp = "^[A-Za-z\\d]{7,}$")
    private String password;

    @NotEmpty(message = "email is empty")
    @Email
    private String email;

    @NotEmpty(message = "role is empty")
    @Pattern(regexp = "^(Admin|Customer)")
    private String role;

    @NotNull(message = "balance is empty")
    @Positive
    private Double balance;

    private ArrayList<Double> purshseHistory= new ArrayList<>();
    private ArrayList<String> generatedGiftsCards = new ArrayList<>();

}
