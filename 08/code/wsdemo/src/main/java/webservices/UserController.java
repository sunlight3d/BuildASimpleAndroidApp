package webservices;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;

import database.Database;
import database.MyException;
import models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //http://localhost:8080/login?email=hoang@gmail.com&password=123456
    @PostMapping("/login")
    public Hashtable<String, Object> login(
                                            @RequestParam(value = "name", defaultValue="") String name,
                                            @RequestParam String email,
                                           @RequestParam(value = "password", defaultValue="") String password,
                                           @RequestParam(value = "userType", defaultValue="default") String userType) {
        Hashtable hashtable = new Hashtable();
        try {
            User loggedInUser = Database.getInstance().login(name, email, password, userType);
            hashtable.put("result", "ok");
            hashtable.put("data", loggedInUser);
            hashtable.put("message", userType.equals("facebook")?"Login Facebook successfully":"Login user successfully");
        }catch (MyException e) {
            hashtable.put("message", e.toString());
            hashtable.put("result", "failed");
        } finally {
            return hashtable;
        }

    }
    //http://localhost:8080/register?email=hoang@gmail.com&password=123456&userType=default
    @PostMapping("/register")
    public Hashtable<String, Object> register(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam(value="name", defaultValue="") String name,
                                              @RequestParam(value="imageUrl", defaultValue="") String imageUrl,
                                              @RequestParam(value="userType", defaultValue="default") String userType
                                              ) {
        Database.getInstance().register(email, name, password, imageUrl, userType);
        Hashtable hashtable = new Hashtable();
        hashtable.put("result", "ok");
        hashtable.put("data", "");
        hashtable.put("message", String.format("Register api with email = %s, " +
                "password = %s, userType = %s, " +
                "name = %s, imageUrl = %s ", email, password, userType, name, imageUrl));
        return hashtable;
    }

}
