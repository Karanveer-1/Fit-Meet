package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class CulturalEvents extends Category {
    private String name;

    private String type;

    private List<CEFeatures> features;

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

    public void setFeatures(List<CEFeatures> features) {
        this.features = features;
    }

    @Override
    public List<CEFeatures> getFeatures() {
        return this.features;
    }

}

class CEGeometry {
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

