package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantss = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;

    public String addMerchantStock(MerchantStock merchantStock) {
        String checker= "";
        for (Merchant m: merchantService.getMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantid())) {
                checker += "m";
            }
        }
        for (Product p: productService.getProducts()) {
            if (p.getId().equals(merchantStock.getProductId())) {
                checker += "p";
            }
        }
        if (checker.equals("mp")) {
        merchantss.add(merchantStock);
        return "done";
        }else return checker;
    }
    public ArrayList<MerchantStock> getMerchantss() {
        return merchantss;
    }
    public String updateMerchantStock(String id,MerchantStock merchantStock) {
        String checker = "";
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantid())) {
                checker += "m";
            }
        }
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(merchantStock.getProductId())) {
                checker += "p";
            }
        }
        if (checker.equals("mp")) {
            for (int i = 0; i < merchantss.size(); i++) {
                if (merchantss.get(i).getId().equals(id)) {
                    merchantss.set(i, merchantStock);
                    return "done";
                }
            }
        }
        return checker;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantss.size(); i++) {
            if (merchantss.get(i).getId().equals(id)) {
                merchantss.remove(i);
                return true;
            }
        }
        return false;
    }

    //Create endpoint where merchant can add more stocks of product to a
    //merchant
    //Stock
    //• this endpoint should accept a product id and merchant id and the amount of additional
    //stock.

    public boolean addMoreStocks(String merchantid, String productId, int stock){
        for (int i = 0; i < merchantss.size(); i++) {
            if (merchantss.get(i).getMerchantid().equals(merchantid)) {
                if (merchantss.get(i).getProductId().equals(productId)) {
                    merchantss.get(i).setStock(merchantss.get(i).getStock()+stock);
                    return true;
                }
            }
        }
        return false;
    }

}
