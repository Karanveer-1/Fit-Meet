package ca.bcit.fitmeet.dashboard.model;

import java.io.Serializable;

public abstract class Feature implements Serializable {
    public abstract Property getProperties();
    public abstract Geometry getGeometry();
}
