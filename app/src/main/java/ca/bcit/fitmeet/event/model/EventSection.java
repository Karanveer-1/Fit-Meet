package ca.bcit.fitmeet.model;

import java.util.ArrayList;

public class EventSection {
    private String sectionHeading;
    private ArrayList<Event> events;

    public EventSection() {
    }

    public EventSection(String sectionHeading, ArrayList<Event> events) {
        this.sectionHeading = sectionHeading;
        this.events = events;
    }

    public void setSectionHeading(String sectionHeading) {
        this.sectionHeading = sectionHeading;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
