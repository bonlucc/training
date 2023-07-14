package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "categories_sequence"

    )
    private Long productId;
    private String productName;
    private String otherData;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "product_product_colors_map",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "productId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "product_colors_id",
                    referencedColumnName = "productColorsId"
            )
    )
    private List<ProductColors> productColors;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "productId"
    )
    private List<ProductCategories> productCategories;

}
