package webservices;

import java.util.Hashtable;

import database.Database;
import models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //http://localhost:8080/login?email=hoang@gmail.com&password=123456
    @PostMapping("/users/login")
    public Hashtable<String, Object> login(
                                            @RequestParam(value = "name", defaultValue="") String name,
                                            @RequestParam String email,
                                           @RequestParam(value = "password", defaultValue="") String password,
                                           @RequestParam(value = "userType", defaultValue="default") String userType) {
        try {
            User loggedInUser = Database.getInstance().login(name, email, password, userType);
            if(loggedInUser == null) {
                return ResponseObject.create("failed", "", "Wrong email or password");
            }
            return ResponseObject.create("ok",
                    loggedInUser,
                    userType.equals("facebook")?"Login Facebook successfully":"Login user successfully");

        }catch (Exception e) {
            return ResponseObject.create("failed", "", "Cannot login, error: "+e.toString());
        }
    }
    //http://localhost:8080/register?email=hoang@gmail.com&password=123456&userType=default
    //ko co register Facebook !
    @PostMapping("/users/register")
    public Hashtable<String, Object> register(@RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam(value="name", defaultValue="") String name,
                                              @RequestParam(value="imageUrl", defaultValue="") String imageUrl,
                                              @RequestParam(value="userType", defaultValue="default") String userType
                                              ) {
        try {
            User newUser = Database.getInstance().register(email, name, password, imageUrl, userType);
            if(newUser == null) {
                return ResponseObject.create("failed", "", "Wrong email or password");
            }
            return ResponseObject.create("ok",
                    newUser,
                    "Register user successfully");

        }catch (Exception e) {
            return ResponseObject.create("failed", "", "Cannot register, error: "+e.toString());
        }
    }

}
