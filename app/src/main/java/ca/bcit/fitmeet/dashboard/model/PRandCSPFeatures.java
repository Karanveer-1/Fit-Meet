package ca.bcit.fitmeet.dashboard.model;

public class PRandCSPFeatures extends Feature {
    private String type;

    private PRandCSPGeometry geometry;

    private PRandCSPProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(PRandCSPGeometry geometry) {
        this.geometry = geometry;
    }

    public PRandCSPGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(PRandCSPProperties properties) {
        this.properties = properties;
    }

    public PRandCSPProperties getProperties() {
        return this.properties;
    }
}

