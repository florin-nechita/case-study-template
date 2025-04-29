package com.rohlikgroup.casestudy.service;


import com.rohlikgroup.casestudy.dto.ProductDto;

import java.util.List;

public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param product the product to create
     * @return the created product
     */
    ProductDto createProduct(ProductDto product);

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     */
    void deleteProduct(Long productId);

    /**
     * Updates a product.
     *
     * @param productId      the ID of the product to update
     * @param updatedProduct the updated product
     * @return the updated product
     */
    ProductDto updateProduct(Long productId, ProductDto updatedProduct);

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    List<ProductDto> getProducts();

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the product with the specified ID
     */
    ProductDto getProduct(Long productId);

}
