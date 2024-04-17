package com.challengetwo.salesmanagementsystem.productmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.AddProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.EditProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import com.challengetwo.salesmanagementsystem.productmanagement.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    void testThatProductCanBeAddedToInventory() {
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductName("IPhone 14 pro");
        addProductRequest.setProductQuantity(5);
        addProductRequest.setCategory(String.valueOf(ProductCategory.COMPUTING));
        addProductRequest.setUnitPrice(560000.00);
        Response response = productService.addProductToInventory(addProductRequest);
        assertEquals("Product added successfully to inventory", response.getMessage());
    }

    @Test
    void testThatProductExists() {
        Product product = productService.getProduct(1L);
        assertNotNull(product);
    }

    @Test
    void testThatProductCanBeUpdated() {
        EditProductRequest editProductRequest = new EditProductRequest();
        editProductRequest.setProductName("Smart watch I9");
        editProductRequest.setProductQuantity(52);
        editProductRequest.setCategory(String.valueOf(ProductCategory.COMPUTING));
        editProductRequest.setUnitPrice(100000.00);
        Response response = productService.updateProduct(102L, editProductRequest);
        assertEquals("Product updated successfully!!", response.getMessage());
    }

    @Test
    void testThatProductCanBeDeleted() {
        Response response = productService.deleteProduct(1L);
        assertEquals("Product deleted from Inventory", response.getMessage());
    }
}