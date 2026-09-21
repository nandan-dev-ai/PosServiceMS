package com.nandan.pos.product;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(length = 1000)
    private String description;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitPrice;

    @Column(nullable = false, unique = true, length = 80)
    private String sku;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(length = 80)
    private String barcode;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;

    protected Product() {
    }

    public Product(String name, String model, String description, BigDecimal unitPrice,
                   String sku, Integer stockQuantity, String barcode, String actor) {
        this.name = name;
        this.model = model;
        this.description = description;
        this.unitPrice = unitPrice;
        this.sku = sku;
        this.stockQuantity = stockQuantity;
        this.barcode = barcode;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.createdBy = actor;
        this.updatedBy = actor;
    }

    public void update(String name, String model, String description, BigDecimal unitPrice,
                       String sku, Integer stockQuantity, String barcode, String actor) {
        this.name = name;
        this.model = model;
        this.description = description;
        this.unitPrice = unitPrice;
        this.sku = sku;
        this.stockQuantity = stockQuantity;
        this.barcode = barcode;
        this.updatedAt = Instant.now();
        this.updatedBy = actor;
    }

    public void deactivate(String actor) {
        this.active = false;
        this.updatedAt = Instant.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getDescription() { return description; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public String getSku() { return sku; }
    public Integer getStockQuantity() { return stockQuantity; }
    public String getBarcode() { return barcode; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}