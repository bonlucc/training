package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/product/api")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/read/All")
    public List<Product> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/read/total")
    public int getAllProductsNumber(){

        return productService.getAllProducts().size();
    }

    @GetMapping("/read/{id}")
    public Product getProduct(@PathVariable Long id){

        return productService.getProduct(id);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/read/Page")
    public List<Product> getPage(@RequestParam("page") int page, @RequestParam("size") int size){
        //log.info(productService.getAllProducts().toString());
        return productService.getProductsPage(page, size);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody @NotNull ProductModel productModel){

        productService.saveProduct(productModel);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductModel productModel) throws ResourceNotFoundException {
        productService.updateProduct(productModel, id);
    }

}
