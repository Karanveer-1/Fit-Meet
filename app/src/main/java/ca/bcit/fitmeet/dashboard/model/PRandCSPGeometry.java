package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class PRandCSPGeometry {
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
