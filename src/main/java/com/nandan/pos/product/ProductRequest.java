package com.nandan.pos.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 100) String model,
        @Size(max = 1000) String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal unitPrice,
        @NotBlank @Size(max = 80) String sku,
        @NotNull @Min(0) Integer stockQuantity,
        @Size(max = 80) String barcode) {
}