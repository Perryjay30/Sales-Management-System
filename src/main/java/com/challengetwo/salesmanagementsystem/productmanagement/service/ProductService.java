package com.challengetwo.salesmanagementsystem.productmanagement.service;

import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.AddProductRequest;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.EditProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;

import java.util.List;

public interface ProductService {
    Response addProductToInventory(AddProductRequest addProductRequest);
    Product getProduct(Long productId);
    Response updateProduct(Long productId, EditProductRequest editProductRequest);
    Response deleteProduct(Long productId);
    List<Product> getAllProducts();
}
