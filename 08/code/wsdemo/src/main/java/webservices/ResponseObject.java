package webservices;

import java.util.Hashtable;

public class ResponseObject {
    //factory method
    public static Hashtable<String, Object> create(String status, Object data, String message) {
        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("status", status);
        hashtable.put("data", data);
        hashtable.put("message", message);
        return hashtable;
    }
}
