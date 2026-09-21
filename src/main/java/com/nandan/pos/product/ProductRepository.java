package com.nandan.pos.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndActiveTrue(Long id);
    boolean existsBySkuAndActiveTrue(String sku);
    boolean existsBySkuAndIdNotAndActiveTrue(String sku, Long id);

    @Query("""
            SELECT product FROM Product product
            WHERE product.active = true
              AND (:search = '' OR
                   LOWER(product.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(product.model) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(product.description) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(product.sku) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(product.barcode) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Product> searchActive(@Param("search") String search, Pageable pageable);
}