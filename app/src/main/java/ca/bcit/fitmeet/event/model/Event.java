package ca.bcit.fitmeet.event.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {
    private String eventId;
    private String hostToken;
    private String eventName;
    private String description;
    private String location;
    private Date dateTime;
    private ArrayList<String> eventTags;
    private String imageReference;

    public Event() { }

    public Event(String eventId, String hostToken, String eventName, String description, String location, Date dateTime, ArrayList<String> eventTags, String imageReference) {
        this.eventId = eventId;
        this.hostToken = hostToken;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.eventTags = eventTags;
        this.imageReference = imageReference;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHostToken() {
        return hostToken;
    }

    public void setHostToken(String hostToken) {
        this.hostToken = hostToken;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getEventTags() {
        return eventTags;
    }

    public void setEventTags(ArrayList<String> eventTags) {
        this.eventTags = eventTags;
    }

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eventId", eventId);
        result.put("hostToken", hostToken);
        result.put("eventName", eventName);
        result.put("description", description);
        result.put("location", location);
        result.put("dateTime", dateTime);
        result.put("eventTags", eventTags);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", hostToken='" + hostToken + '\'' +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", dateTime=" + dateTime +
                ", eventTags=" + eventTags +
                '}';
    }

}
