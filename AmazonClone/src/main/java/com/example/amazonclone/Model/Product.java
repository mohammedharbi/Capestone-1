package com.example.amazonclone.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    //• id (must not be empty).
    //• name (must not be empty, have to be more than 3 length long).
    //• price (must not be empty, must be positive number).
    //• categoryID (must not be empty).

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "name is empty")
    @Size(min = 3, message = "name must be at least 3 letters")
    private String name;

    @NotNull(message = "price is empty")
    @Positive
    private double price;

    @NotEmpty(message = "category id is empty")
    private String categoryid;


}
