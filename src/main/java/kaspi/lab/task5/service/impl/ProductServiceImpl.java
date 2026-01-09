package kaspi.lab.task5.service.impl;

import kaspi.lab.task5.dto.ProductDto;
import kaspi.lab.task5.entity.Product;
import kaspi.lab.task5.mapper.ProductMapper;
import kaspi.lab.task5.rep.ProductRep;
import kaspi.lab.task5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRep productRep;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRep.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        Product savedProduct = productRep.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existingProduct = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (dto.getName() != null) existingProduct.setName(dto.getName());
        if (dto.getPrice() != null) existingProduct.setPrice(dto.getPrice());
        Product updatedProduct = productRep.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRep.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRep.delete(existingProduct);
    }
}
