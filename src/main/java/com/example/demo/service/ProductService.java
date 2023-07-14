package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.model.ProductModel;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService (ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public void initialize(){
        if (!productRepository.findAll().isEmpty()){
            return;
        }
        Product product1 = Product.builder()
                        .productName("ProductName1")
                        .otherData("ProductType1")
                        .build(),
                product2 = Product.builder()
                        .productName("ProductName2")
                        .otherData("ProductType2")
                        .build(),
                product3 = Product.builder()
                        .productName("ProductName3")
                        .otherData("ProductType3")
                        .build(),
                product4 = Product.builder()
                        .productName("ProductName4")
                        .otherData("ProductType4")
                        .build(),
                product5 = Product.builder()
                        .productName("ProductName5")
                        .otherData("ProductType5")
                        .build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);
        productRepository.saveAll(products);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.getReferenceById(id);
    }

    public void saveProduct(ProductModel productModel){
        Product product = Product.builder()
                .productName(productModel.getProductName())
                .otherData(productModel.getOtherData())
                .build();
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void updateProduct(ProductModel productModel, Long id){
        Product product = productRepository.getReferenceById(id);
        product.setProductName(product.getProductName());
        product.setOtherData(productModel.getOtherData());
        productRepository.save(product);

    }


    public List<Product> getProductsPage(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
