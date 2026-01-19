package kaspi.lab.task5.rep;

import kaspi.lab.task5.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRep extends R2dbcRepository<Product, Long> { }