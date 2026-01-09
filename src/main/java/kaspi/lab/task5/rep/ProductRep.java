package kaspi.lab.task5.rep;

import kaspi.lab.task5.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRep extends JpaRepository<Product, Long> { }