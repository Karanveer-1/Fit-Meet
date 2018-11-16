package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class SportsFields extends Category {
    private String name;

    private String type;

    private List<SFFeatures> features;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setFeatures(List<SFFeatures> features) {
        this.features = features;
    }

    public List<SFFeatures> getFeatures() {
        return this.features;
    }
}

class SFGeometry {
    private String type;

    private List<Double> coordinates;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Double> getCoordinates() {
        return this.coordinates;
    }
}

class SFProperties {
    private String TYPE;

    private String PARK;

    private String ACTIVITIES;

    private String X;

    private String Y;

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setPARK(String PARK) {
        this.PARK = PARK;
    }

    public String getPARK() {
        return this.PARK;
    }

    public void setACTIVITIES(String ACTIVITIES) {
        this.ACTIVITIES = ACTIVITIES;
    }

    public String getACTIVITIES() {
        return this.ACTIVITIES;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getX() {
        return this.X;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

    public String getY() {
        return this.Y;
    }
}

class SFFeatures {
    private String type;

    private SFGeometry geometry;

    private SFProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(SFGeometry geometry) {
        this.geometry = geometry;
    }

    public SFGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(SFProperties properties) {
        this.properties = properties;
    }

    public SFProperties getProperties() {
        return this.properties;
    }
}
