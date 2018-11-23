package ca.bcit.fitmeet.dashboard.model;

public class OLDAFeatures extends Feature {
    private String type;

    private OLDAGeometry geometry;

    private OLDAProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(OLDAGeometry geometry) {
        this.geometry = geometry;
    }

    public OLDAGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(OLDAProperties properties) {
        this.properties = properties;
    }

    public OLDAProperties getProperties() {
        return this.properties;
    }
}
