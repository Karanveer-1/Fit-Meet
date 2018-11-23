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

