package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public boolean addProduct(Product product) {
        boolean categoryidvalid = false;

        for (Category c : categoryService.getCategories()){
            if (c.getId().equals(product.getCategoryid())){
                categoryidvalid = true;
            }
        }
        if (categoryidvalid){
        products.add(product);
        return true;
        }else return false;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public boolean deleteProduct(String id) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean updateProduct(String id,Product product) {
        boolean categoryidvalid = false;

        for (Category c : categoryService.getCategories()) {
            if (c.getId().equals(product.getCategoryid())) {
                categoryidvalid = true;
            }
        }

        if (categoryidvalid) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(id)) {
                    products.set(i, product);
                    return true;
                }
            }
        }
        return false;
    }

}
