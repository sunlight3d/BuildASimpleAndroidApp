package webservices;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    //some query functions
    List<Product> findByUserId(String userId);
    Product findByProductId(String productId);

}
