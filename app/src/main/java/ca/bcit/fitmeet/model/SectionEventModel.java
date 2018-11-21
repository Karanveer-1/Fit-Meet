package ca.bcit.fitmeet.model;

import java.util.List;

public class SectionEventModel {
    private String sectionHeading;
    private List<SingleEventModel> event;

    public SectionEventModel() { }

    public SectionEventModel(String sectionHeading, List<SingleEventModel> event) {
        this.sectionHeading = sectionHeading;
        this.event = event;
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public void setSectionHeading(String sectionHeading) {
        this.sectionHeading = sectionHeading;
    }

    public List<SingleEventModel> getEvent() {
        return event;
    }

    public void setEvent(List<SingleEventModel> event) {
        this.event = event;
    }

}
