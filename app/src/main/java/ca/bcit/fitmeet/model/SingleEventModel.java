package ca.bcit.fitmeet.model;

public class SingleEventModel {
    private String heading, description, dateTime, image;

    public SingleEventModel() { }

    public SingleEventModel(String heading, String description, String dateTime) {
        this.heading = heading;
        this.description = description;
        this.dateTime = dateTime;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

}
