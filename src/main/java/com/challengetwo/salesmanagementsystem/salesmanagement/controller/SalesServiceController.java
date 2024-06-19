package com.challengetwo.salesmanagementsystem.salesmanagement.controller;

import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class SalesServiceController {

    private final SalesService salesService;

    @PostMapping("/createSales")
    public ResponseEntity<?> createSales(@Valid @RequestBody CreateSalesRequest createSalesRequest) {
       return ResponseEntity.ok(salesService.createSales(createSalesRequest));
    }

    @GetMapping("/getSalesById/{salesId}")
    public ResponseEntity<?> getSalesById(@PathVariable Long salesId) {
        return ResponseEntity.ok(salesService.getSales(salesId));
    }

    @PutMapping("/editSales/{salesId}")
    public ResponseEntity<?> editSales(@PathVariable Long salesId, @Valid @RequestBody EditSales editSale) {
        return ResponseEntity.ok(salesService.editQuantityAndPricesOfSale(salesId, editSale));
    }

    @GetMapping("/getAllSales")
    public ResponseEntity<?> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }


}
