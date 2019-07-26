package webservices;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;

import database.Database;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //http://localhost:8080/login?email=hoang@gmail.com&password=123456
    @PostMapping("/login")
    public Hashtable<String, Object> login(@RequestParam String email, @RequestParam String password) {
        Hashtable hashtable = new Hashtable();
        hashtable.put("result", "ok");
        hashtable.put("data", "");
        hashtable.put("message", String.format("login api with email = %s, password = %s", email, password));
        return hashtable;
    }
    //http://localhost:8080/register?email=hoang@gmail.com&password=123456&userType=default
    @PostMapping("/register")
    public Hashtable<String, Object> register(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam(value="name", defaultValue="") String name,
                                              @RequestParam(value="imageUrl", defaultValue="") String imageUrl,
                                              @RequestParam(value="userType", defaultValue="default") String userType
                                              ) {
        Database database = Database.getInstance();
        Hashtable hashtable = new Hashtable();
        hashtable.put("result", "ok");
        hashtable.put("data", "");
        hashtable.put("message", String.format("Register api with email = %s, " +
                "password = %s, userType = %s, " +
                "name = %s, imageUrl = %s ", email, password, userType, name, imageUrl));
        return hashtable;
    }

}
