package webservices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webservices.database.Database;
import webservices.models.Place;

import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Hashtable;

@RestController
public class PlaceController {
    //Examples:
    //http://localhost:8080/places/insert?userId=13&placeName=Bana Hills&address=vietnam&lat=21.024887&lon=105.857822
    @PostMapping("/places/insert")
    public Hashtable<String, Object> insert(@RequestParam @NotNull Integer userId,
                                            @RequestParam(value="placeName", defaultValue="") String placeName,
                                            @RequestParam(value="address", defaultValue="") String address,
                                            @RequestParam @NotNull Double lat,
                                            @RequestParam @NotNull Double lon
    ) {
        try {
            Place newPlace = Database.getInstance().insertPlace(userId, placeName, address,lat,lon);
            if(newPlace == null) {
                return ResponseObject.create("failed", "", "Cannot insert Place");
            }
            return ResponseObject.create("ok",
                    newPlace,
                    "Insert place successfully");
        }catch (Exception e) {
            return ResponseObject.create("failed", "", "Cannot register, error: "+e.toString());
        }
    }
    @GetMapping("/places/query")
    public Hashtable<String, Object> query(@RequestParam @NotNull Integer userId,
                                            @RequestParam(value="offset", defaultValue="0") Integer offset,
                                            @RequestParam(value="limit", defaultValue="10") Integer limit
    ) {
        try {
            ArrayList<Place> places = Database.getInstance().queryPlaces(userId, offset, limit);
            return ResponseObject.create("ok", places,
                    "Query places successfully");
        }catch (Exception e) {
            return ResponseObject.create("failed", "", "Cannot query places.Error: "+e.toString());
        }

    }
}
