package org.baga.pharmacypoint.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BestOffersReport {
    private String productName;
    private String discountName;
    private String discountConditions;
    private int discountPercentage;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
    private BigDecimal discountedPrice;
}
