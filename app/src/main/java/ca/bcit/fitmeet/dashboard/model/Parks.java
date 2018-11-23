package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class Parks extends Category {
    private String name;

    private String type;

    private List<PFeatures> features;

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

    public void setFeatures(List<PFeatures> features) {
        this.features = features;
    }

    public List<PFeatures> getFeatures() {
        return this.features;
    }
}

