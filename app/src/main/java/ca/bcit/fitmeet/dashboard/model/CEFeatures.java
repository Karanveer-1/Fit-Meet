package ca.bcit.fitmeet.dashboard.model;

public class CEFeatures {
    private String type;

    private CEGeometry geometry;

    private CEProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(CEGeometry geometry) {
        this.geometry = geometry;
    }

    public CEGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(CEProperties properties) {
        this.properties = properties;
    }

    public CEProperties getProperties() {
        return this.properties;
    }
}