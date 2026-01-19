package kaspi.lab.task5.service.impl;

import kaspi.lab.task5.dto.DeliveryRequest;
import kaspi.lab.task5.dto.ProductDto;
import kaspi.lab.task5.mapper.ProductMapper;
import kaspi.lab.task5.rep.ProductRep;
import kaspi.lab.task5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRep productRep;
    private final ProductMapper productMapper;
    private final WebClient webClient;

    @Override
    public Mono<ProductDto> getProductById(Long id) {
        return productRep.findById(id)
                .map(productMapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productRep.findAll()
                .map(productMapper::toDto);
    }

    @Override
    public Mono<ProductDto> createProduct(ProductDto dto) {
        return productRep.save(productMapper.toEntity(dto))
                .flatMap(savedProduct -> {
                    if (savedProduct.getAddress() != null) {
                        DeliveryRequest request = new DeliveryRequest();
                        request.setProductId(savedProduct.getId());
                        request.setAddress(savedProduct.getAddress());
                        return webClient.post()
                                .uri("http://localhost:8081/delivery")
                                .bodyValue(request)
                                .retrieve()
                                .bodyToMono(String.class)
                                .thenReturn(productMapper.toDto(savedProduct))
                                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create delivery " + e.getMessage())));
                    }
                    return Mono.just(productMapper.toDto(savedProduct));
                });
    }

    @Override
    public Mono<ProductDto> updateProduct(Long id, ProductDto dto) {
        return productRep.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(existingProduct -> {
                    if (dto.getName() != null) existingProduct.setName(dto.getName());
                    if (dto.getPrice() != null) existingProduct.setPrice(dto.getPrice());
                    if (dto.getAddress() != null) existingProduct.setAddress(dto.getAddress());
                    return productRep.save(existingProduct);
                })
                .map(productMapper::toDto);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRep.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(productRep::delete);
    }
}
