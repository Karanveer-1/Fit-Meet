package ca.bcit.fitmeet.dashboard.model;

public class PFeatures {
    private String type;

    private PGeometry geometry;

    private PProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(PGeometry geometry) {
        this.geometry = geometry;
    }

    public PGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(PProperties properties) {
        this.properties = properties;
    }

    public PProperties getProperties() {
        return this.properties;
    }
}