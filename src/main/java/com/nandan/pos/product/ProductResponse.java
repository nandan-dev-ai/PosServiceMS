package com.nandan.pos.product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        String model,
        String description,
        BigDecimal unitPrice,
        String sku,
        Integer stockQuantity,
        String barcode,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getModel(),
                product.getDescription(), product.getUnitPrice(), product.getSku(),
                product.getStockQuantity(), product.getBarcode(), product.isActive(),
                product.getCreatedAt(), product.getUpdatedAt(), product.getCreatedBy(),
                product.getUpdatedBy());
    }
}