package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        String check_return = merchantStockService.addMerchantStock(merchantStock);

        if (check_return.equals("done")) {
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock added"));
        }else if (check_return.equals("m")) {
            return ResponseEntity.status(400).body(new ApiResponse("product ID does not exist"));
        }else return ResponseEntity.status(400).body(new ApiResponse("merchant ID does not exist"));
    }

    @GetMapping("/get")
    public ResponseEntity getMerchantStock() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantss());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock,Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        String isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        return switch (isUpdated) {
            case "done" -> ResponseEntity.status(200).body(new ApiResponse("merchant stock updated"));
            case "m" -> ResponseEntity.status(404).body(new ApiResponse("product ID does not exist"));
            case "p" -> ResponseEntity.status(404).body(new ApiResponse("merchant ID does not exist"));
            default -> ResponseEntity.status(400).body(new ApiResponse("MerchantStock not updated"));
        };

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted"));
        }else return ResponseEntity.status(400).body(new ApiResponse("MerchantStock not deleted"));
    }

    @PutMapping("/addMoreStocks/{merchantid}/{productId}/{stock}")
    public ResponseEntity addMoreStocks(@PathVariable String merchantid, @PathVariable String productId, @PathVariable int stock) {

        boolean isAdded = merchantStockService.addMoreStocks(merchantid, productId, stock);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not updated"));
    }

}
