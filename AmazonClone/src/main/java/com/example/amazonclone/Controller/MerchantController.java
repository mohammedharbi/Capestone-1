package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
    }

    @GetMapping("/get")
    public ResponseEntity getMerchant() {
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        boolean isUpdated =merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated"));
        }else return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted"));
        }else return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
    }

}