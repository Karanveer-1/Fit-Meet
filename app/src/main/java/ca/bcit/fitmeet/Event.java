package ca.bcit.fitmeet;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {

    private String eventId;
    private String eventName;
    private String hostToken;
    private String time;
    private String location;
    private String description;


//REQUIRED default constructor
   public Event(){

    }

    public Event(String eventId, String eventName, String hostToken, String time, String location, String description) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.hostToken = hostToken;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("hostToken", hostToken);
        result.put("eventId", eventId);
        result.put("eventName", eventName);
        result.put("time", time);
        result.put("location", location);
        result.put("description", description);
        return result;
    }




}
