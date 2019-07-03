package webservices;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity //Hibernate's entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String productName;
    private Integer productYear;
    private Double price;
    private String productDescription;
    //"Foreign key"
    private String userId;
    //Fix "null id"
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product(String productName, Integer productYear, Double price,
                   String productDescription, String userId) {
        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.productDescription = productDescription;
        this.userId = userId;
        this.productId = UUID.randomUUID().toString();
    }
    public Product() {}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
