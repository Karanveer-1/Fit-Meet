package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class PRandCSP extends Category {
    private String name;

    private String type;

    private List<PRandCSPFeatures> features;

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

    public void setFeatures(List<PRandCSPFeatures> features) {
        this.features = features;
    }

    public List<PRandCSPFeatures> getFeatures() {
        return this.features;
    }
}
