package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PricingAnalysis {
    private double averagePrice;
    private double lowestPrice;
    private double highestPrice;
}
