package org.baga.pharmacypoint.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesForecastReport {
    private String productName;
    private int forecastedQuantitySold;
    private BigDecimal forecastedSalesAmount;
}
