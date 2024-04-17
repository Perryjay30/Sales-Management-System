package com.challengetwo.salesmanagementsystem.productmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.AddProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.EditProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import com.challengetwo.salesmanagementsystem.productmanagement.model.ProductCategory;
import com.challengetwo.salesmanagementsystem.productmanagement.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Response addProductToInventory(AddProductRequest addProductRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.convertValue(addProductRequest, Product.class);
        productRepository.save(product);
        productRepository.updateAvailabilityBasedOnQuantity();

        return new Response("Product added successfully to inventory");
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findProductById(productId)
                .orElseThrow(() -> new SalesManagementSystemException("Product is unavailable!!"));
    }

    @Override
    public Response updateProduct(Long productId, EditProductRequest editProductRequest) {
        Product existingProduct = getProduct(productId);
        existingProduct.setProductName(editProductRequest.getProductName() != null && !editProductRequest.getProductName()
                .equals("") ? editProductRequest.getProductName() : existingProduct.getProductName());
        existingProduct.setProductQuantity(editProductRequest.getProductQuantity() != 0
                ? editProductRequest.getProductQuantity() : existingProduct.getProductQuantity());
        existingProduct.setCategory(editProductRequest.getCategory() != null && !editProductRequest.getCategory()
                .equals("") ? ProductCategory.valueOf(editProductRequest.getCategory()) : existingProduct.getCategory());
        existingProduct.setUnitPrice(editProductRequest.getUnitPrice() != 0.00 ? editProductRequest.getUnitPrice() : existingProduct.getUnitPrice());
        productRepository.save(existingProduct);
        productRepository.updateAvailabilityBasedOnQuantity();
        return new Response("Product updated successfully!!");
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product existingProduct = getProduct(productId);
        productRepository.delete(existingProduct);
        return new Response("Product deleted from Inventory");
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
