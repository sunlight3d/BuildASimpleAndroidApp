package webservices;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    //some query functions
    List<ProductRepository.Product> findByUserId(String userId);
    ProductRepository.Product findByProductId(String productId);
    @Entity //Hibernate's entity
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        //fix "null Id"
        private String productId;
        private String productName;
        private Integer productYear;
        private Double price;
        private String productDescription;
        //"Foreign key"
        private String userId;
        public Product(String productId, String productName, Integer productYear, Double price,
                       String productDescription, String userId) {
            this.productId = productId;
            this.productName = productName;
            this.productYear = productYear;
            this.price = price;
            this.productDescription = productDescription;
            this.userId = userId;
        }
        public Product() {}
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getProductYear() {
            return productYear;
        }

        public void setProductYear(Integer productYear) {
            this.productYear = productYear;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
