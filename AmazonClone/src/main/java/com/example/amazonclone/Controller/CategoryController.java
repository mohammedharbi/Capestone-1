package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Category updated"));
        }else return ResponseEntity.status(400).body(new ApiResponse("Category not updated"));
        }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){

        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {return ResponseEntity.status(200).body(new ApiResponse("Category deleted"));
        }else return ResponseEntity.status(400).body(new ApiResponse("Category not deleted"));
    }

}