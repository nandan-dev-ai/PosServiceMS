package com.nandan.pos.product;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository priceHistoryRepository;

    public ProductService(ProductRepository productRepository,
                          ProductPriceHistoryRepository priceHistoryRepository) {
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    @Transactional
    public ProductResponse add(ProductRequest request, String actor) {
        if (productRepository.existsBySkuAndActiveTrue(request.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An active product already uses this SKU");
        }
        Product product = new Product(request.name(), request.model(), request.description(),
                request.unitPrice(), request.sku(), request.stockQuantity(), request.barcode(), actor);
        productRepository.save(product);
        priceHistoryRepository.save(new ProductPriceHistory(product, request.unitPrice(), Instant.now(), actor));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request, String actor) {
        Product product = findActive(id);
        if (productRepository.existsBySkuAndIdNotAndActiveTrue(request.sku(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An active product already uses this SKU");
        }
        if (product.getUnitPrice().compareTo(request.unitPrice()) != 0) {
            Instant changedAt = Instant.now();
            priceHistoryRepository.findFirstByProductIdAndEffectiveToIsNullOrderByEffectiveFromDesc(id)
                    .ifPresent(current -> current.closeAt(changedAt));
            priceHistoryRepository.save(new ProductPriceHistory(product, request.unitPrice(), changedAt, actor));
        }
        product.update(request.name(), request.model(), request.description(), request.unitPrice(),
                request.sku(), request.stockQuantity(), request.barcode(), actor);
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id, String actor) {
        Product product = findActive(id);
        product.deactivate(actor);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(String search, Pageable pageable) {
        String normalizedSearch = search == null ? "" : search.trim();
        return productRepository.searchActive(normalizedSearch, pageable).map(ProductResponse::from);
    }

    private Product findActive(Long id) {
        return productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}