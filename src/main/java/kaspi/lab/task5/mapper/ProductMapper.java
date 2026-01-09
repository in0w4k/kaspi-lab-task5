package kaspi.lab.task5.mapper;

import kaspi.lab.task5.dto.ProductDto;
import kaspi.lab.task5.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto dto);
    ProductDto toDto(Product entity);
}
