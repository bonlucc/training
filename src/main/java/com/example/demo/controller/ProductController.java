package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/product/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/readAll")
    public List<Product> getAllProducts(){
        productService.initialize();
        return productService.getAllProducts();
    }
    @GetMapping("/read/{id}")
    public Product getProduct(@PathVariable Long id){
        return productService.getProduct(id);
    }

    @GetMapping("/readPage")
    public List<Product> getPage(@RequestParam("page") int page, @RequestParam("size") int size){
        productService.initialize();
        return productService.getProductsPage(page, size);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductModel productModel){
        productService.saveProduct(productModel);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
    @PutMapping("/update/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductModel productModel){
        productService.updateProduct(productModel, id);
    }
}
