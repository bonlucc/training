package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.model.ProductModel;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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
                        .build(),
                product6 = Product.builder()
                        .productName("ProductName6")
                        .otherData("ProductType6")
                        .build(),
                product7 = Product.builder()
                        .productName("ProductName7")
                        .otherData("ProductType7")
                        .build(),
                product8 = Product.builder()
                        .productName("ProductName8")
                        .otherData("ProductType8")
                        .build(),
                product9 = Product.builder()
                        .productName("ProductName9")
                        .otherData("ProductType9")
                        .build();

        List<Product> products = List.of(product1, product2, product3, product4, product5,
                                         product6, product7, product8, product9);
        productRepository.saveAll(products);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.getReferenceById(id);
    }

    public void saveProduct(@NotNull ProductModel productModel){
        if (!productModel.getOtherData().isEmpty() && !productModel.getProductName().isEmpty()) {
            Product product = Product.builder()
                    .productName(productModel.getProductName())
                    .otherData(productModel.getOtherData())
                    .build();

            productRepository.save(product);
        }

    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void updateProduct(ProductModel productModel, Long id) throws ResourceNotFoundException {
        if(productRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("Product Not Found");
        Product product = productRepository.findById(id).get();
        if(!Objects.equals(productModel.getProductName(), "")) product.setProductName(productModel.getProductName());
        if(!Objects.equals(productModel.getOtherData(), "")) product.setOtherData(productModel.getOtherData());
        productRepository.save(product);

    }


    public List<Product> getProductsPage(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
