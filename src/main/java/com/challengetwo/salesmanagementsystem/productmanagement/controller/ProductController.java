package com.challengetwo.salesmanagementsystem.productmanagement.controller;


import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.AddProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.dto.request.EditProductRequest;
import com.challengetwo.salesmanagementsystem.productmanagement.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductTOInventory(@RequestBody AddProductRequest addProductRequest) {
        return ResponseEntity.ok(productService.addProductToInventory(addProductRequest));
    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PatchMapping("/updateProduct/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody EditProductRequest editProductRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, editProductRequest));
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}
