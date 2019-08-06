package webservices.controllers;

import java.util.Hashtable;

public class ResponseObject {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    public static Hashtable<String, Object> create(String status, Object data, String message) {
        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("status", status);
        hashtable.put("data", data);
        hashtable.put("message", message);
        return hashtable;
    }
}
