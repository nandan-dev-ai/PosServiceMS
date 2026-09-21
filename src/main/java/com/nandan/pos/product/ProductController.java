package com.nandan.pos.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest request,
                                                       @RequestHeader(value = "X-User-Id", defaultValue = "system") String actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(request, actor));
    }

    @PutMapping("/update-product/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request,
                                         @RequestHeader(value = "X-User-Id", defaultValue = "system") String actor) {
        return productService.update(id, request, actor);
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,
                                              @RequestHeader(value = "X-User-Id", defaultValue = "system") String actor) {
        productService.delete(id, actor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-product")
    public Page<ProductResponse> searchProducts(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return productService.search(search, pageable);
    }
}