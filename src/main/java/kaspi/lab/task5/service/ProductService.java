package kaspi.lab.task5.service;

import kaspi.lab.task5.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> getProductById(Long id);
    Flux<ProductDto> getAllProducts();
    Mono<ProductDto> createProduct(ProductDto dto);
    Mono<ProductDto> updateProduct(Long id, ProductDto dto);
    Mono<Void> deleteProduct(Long id);
}
