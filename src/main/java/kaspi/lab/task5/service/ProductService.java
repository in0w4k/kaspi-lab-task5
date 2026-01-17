package kaspi.lab.task5.service;

import kaspi.lab.task5.dto.ProductDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    ProductDto getProductById(Long id);
    CompletableFuture<List<ProductDto>> getAllProducts();
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id, ProductDto dto);
    void deleteProduct(Long id);
}
