package webservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping(path="/products") //http://localhost:8081/products
public class ProductController {
    @Autowired  //This means to get the bean called productRepository
    private  ProductRepository productRepository;

    @PostMapping(path="/add") //POST http://localhost:8081/products/add
    public @ResponseBody Hashtable addProduct(@RequestParam String productName,
                                              @RequestParam Integer productYear,
                                              @RequestParam Double price,
                                              @RequestParam String productDescription,
                                              @RequestParam String userId
                                              ) {
        Hashtable<String, Object> response = new Hashtable<>();
        String productId = UUID.randomUUID().toString();
        Product newProduct = new Product(productId, productName, productYear, price,
                productDescription,userId);
        productRepository.save(newProduct);
        response.put("result", "ok");
        response.put("data", newProduct);
        response.put("message", "Add new product successfully");
        return response;
    }
    @GetMapping(path="/all")//GET http://localhost:8081/products/all
    public @ResponseBody Hashtable getAllProducts(@RequestParam String userId) {
        Hashtable<String, Object> response = new Hashtable<>();
        List<ProductRepository.Product> products = productRepository.findByUserId(userId);
        response.put("result", "ok");
        response.put("data", products);
        response.put("message", "Query products successfully");
        return response;
    }
    //find Product by ID ?
    @GetMapping(path = "/detailProduct") //GET http://localhost:8081/products/detailProduct
    public @ResponseBody Hashtable getDetailProduct(@RequestParam String productId) {
        Hashtable<String, Object> response = new Hashtable<>();
        try {
            ProductRepository.Product detailProduct = productRepository.findByProductId(productId);
            if(detailProduct == null) {
                response.put("result", "failed");
                response.put("data", "");
                response.put("message", "Cannot find product with id = "+productId);
            } else {
                response.put("result", "ok");
                response.put("data", detailProduct);
                response.put("message", "Query products successfully");
            }


        } catch (Exception e) {
            response.put("result", "failed");
            response.put("data", "");
            response.put("message", "Error:"+e.toString());
        } finally {
            return response;
        }

    }

}
