package webservices.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webservices.database.Database;
import webservices.models.User;

import java.util.Hashtable;

@RestController
public class UserController {
    //http://localhost:8080/users/login?name=Nguyen Duc Hoang&email=hoang@gmail.com&password=123456&userType=
    @PostMapping("/users/login")
    public Hashtable<String, Object> login(
                            @RequestParam(value = "name", defaultValue = "") String name,
                            String email,
                            @RequestParam(value = "password", defaultValue="") String password,
                            @RequestParam(value = "userType", defaultValue="default")String userType) {
        String inputParams = "name = "+name+"email = "+email+"password ="+password+"userType ="+userType;
        //How to return a JSON object ?
        Hashtable<String, Object> hashtable = new Hashtable<>();
        //We must query database, get data, then response to client...
        return hashtable;
    }
    //Register user
    @PostMapping("/users/register")
    public Hashtable<String, Object> register(
                                        @RequestParam String email,
                                        @RequestParam String password,
                                        @RequestParam(value="name", defaultValue="") String name,
                                        @RequestParam(value="imageUrl", defaultValue="") String imageUrl,
                                        @RequestParam(value="userType", defaultValue="default") String userType) {
        Hashtable<String, Object> hashtable = new Hashtable<>();
        //We must insert data to database, get data, then response to client...
        try {
            User newUser = Database.getInstance().register(email, name, password, imageUrl, userType);
            if(newUser == null) {
                return ResponseObject.create(ResponseObject.FAILED, "", "Cannot register user");
            }
            return ResponseObject.create(ResponseObject.SUCCESS, newUser, "Register user successfully");
        }catch (Exception e) {
            return ResponseObject.create(ResponseObject.FAILED, "", "Cannot register, error: "+e.toString());
        }
    }
}
