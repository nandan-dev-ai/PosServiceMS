package com.nandan.pos.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
    Optional<ProductPriceHistory> findFirstByProductIdAndEffectiveToIsNullOrderByEffectiveFromDesc(Long productId);
}