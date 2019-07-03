package webservices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    //some query functions
    List<Product> findByUserId(String userId);
    Product findByProductId(String productId);
}
