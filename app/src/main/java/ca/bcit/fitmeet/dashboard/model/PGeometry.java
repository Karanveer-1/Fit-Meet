package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class PGeometry {
    private String type;

    private List<List<List<Double>>> coordinates;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public List<List<List<Double>>> getCoordinates() {
        return this.coordinates;
    }
}

