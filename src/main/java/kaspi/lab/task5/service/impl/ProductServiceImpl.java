package kaspi.lab.task5.service.impl;

import kaspi.lab.task5.dto.DeliveryRequest;
import kaspi.lab.task5.dto.ProductDto;
import kaspi.lab.task5.entity.Product;
import kaspi.lab.task5.mapper.ProductMapper;
import kaspi.lab.task5.rep.ProductRep;
import kaspi.lab.task5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRep productRep;
    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    @Async("productExecutor")
    public CompletableFuture<List<ProductDto>> getAllProducts() {
        System.out.println("Fetching all products in thread: " + Thread.currentThread().getName());

        List<Product> products = productRep.findAll();
        List<ProductDto> dtos = products.stream()
                .map(productMapper::toDto)
                .toList();

        return CompletableFuture.completedFuture(dtos);
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        Product savedProduct = productRep.save(product);

        if (savedProduct.getAddress() != null) {
            String deliveryUrl = "http://localhost:8081/delivery";
            DeliveryRequest request = new DeliveryRequest();
            request.setProductId(savedProduct.getId());
            request.setAddress(savedProduct.getAddress());
            try {
                restTemplate.postForObject(deliveryUrl, request, String.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create delivery: " + e.getMessage());
            }
        }
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existingProduct = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (dto.getName() != null) existingProduct.setName(dto.getName());
        if (dto.getPrice() != null) existingProduct.setPrice(dto.getPrice());
        if (dto.getAddress() != null) existingProduct.setAddress(dto.getAddress());
        Product updatedProduct = productRep.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRep.delete(existingProduct);
    }
}
