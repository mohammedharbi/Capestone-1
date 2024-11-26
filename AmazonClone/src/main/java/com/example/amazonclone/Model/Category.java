package com.example.amazonclone.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "name is empty")
    @Size(min = 3, message = "name must be at least 3 letters long")
    private String name;

}
