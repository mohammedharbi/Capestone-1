package com.example.amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "id is empty")
    private String id;

    @NotEmpty(message = "product id is empty")
    private String productId;

    @NotEmpty(message = "mechantid is empty")
    private String merchantid;

    @NotNull(message = "stock is empty")
    @Min(value = 10,message = "stock must be at least 10")
    private int stock;

}
