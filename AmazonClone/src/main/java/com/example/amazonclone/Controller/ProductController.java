package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Product;
import com.example.amazonclone.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProduct() {
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isCategoryvalid = productService.addProduct(product);
        if (isCategoryvalid) {
        return ResponseEntity.status(201).body(new ApiResponse("Product added"));
        } else return ResponseEntity.status(400).body("Product not added, Category ID does not match");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated = productService.updateProduct(id,product);
        if (isUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("Product updated"));
        } else {return ResponseEntity.status(400).body(new ApiResponse("Product not updated, Category ID does not match"));}
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {return ResponseEntity.status(201).body(new ApiResponse("Product deleted"));
        } else {return ResponseEntity.status(400).body(new ApiResponse("Product not deleted"));}
    }

}