package webservices;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import database.Database;
import models.Place;
import models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
//REST = Representational State Transfer
@RestController
public class PlaceController {
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
    //http://localhost:8080/places/query?userId=13&offset=0&limit=10
    @GetMapping("/places/query")
    public Hashtable<String, Object> insert(@RequestParam @NotNull Integer userId,
                                            @RequestParam(value="offset", defaultValue="0") Integer offset,
                                            @RequestParam(value="limit", defaultValue="10") Integer limit
    ) {
        try {
            ArrayList<Place> places = Database.getInstance().queryPlaces(userId, offset, limit);
            return ResponseObject.create("ok", places,
                    "Query place successfully");

        }catch (Exception e) {
            return ResponseObject.create("failed", "", "Cannot register, error: "+e.toString());
        }
    }
}
