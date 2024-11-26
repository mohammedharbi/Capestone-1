package com.example.amazonclone.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "name is empty")
    @Size(min = 3, message = "name size must be at least 3 long")
    private String name;
}
