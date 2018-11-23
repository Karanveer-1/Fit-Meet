package ca.bcit.fitmeet.dashboard.model;

public class SFFeatures extends Feature {
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
