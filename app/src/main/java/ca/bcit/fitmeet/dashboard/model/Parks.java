package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class Parks {
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

class PFeatures {
    private String type;

    private PGeometry geometry;

    private PProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(PGeometry geometry) {
        this.geometry = geometry;
    }

    public PGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(PProperties properties) {
        this.properties = properties;
    }

    public PProperties getProperties() {
        return this.properties;
    }
}

class PProperties {
    private String StrName;

    private String StrNum;

    private int OBJECTID;

    private String Name;

    private String Category;

    private String Neighbourhood;

    private String Zoning;

    private String Zone_Category;

    private String Owner;

    private String Surveyed;

    private double Site_Area;

    private String Relationship;

    private double SHAPE_Length;

    private double SHAPE_Area;

    public void setStrName(String StrName) {
        this.StrName = StrName;
    }

    public String getStrName() {
        return this.StrName;
    }

    public void setStrNum(String StrNum) {
        this.StrNum = StrNum;
    }

    public String getStrNum() {
        return this.StrNum;
    }

    public void setOBJECTID(int OBJECTID) {
        this.OBJECTID = OBJECTID;
    }

    public int getOBJECTID() {
        return this.OBJECTID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setNeighbourhood(String Neighbourhood) {
        this.Neighbourhood = Neighbourhood;
    }

    public String getNeighbourhood() {
        return this.Neighbourhood;
    }

    public void setZoning(String Zoning) {
        this.Zoning = Zoning;
    }

    public String getZoning() {
        return this.Zoning;
    }

    public void setZone_Category(String Zone_Category) {
        this.Zone_Category = Zone_Category;
    }

    public String getZone_Category() {
        return this.Zone_Category;
    }

    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    public String getOwner() {
        return this.Owner;
    }

    public void setSurveyed(String Surveyed) {
        this.Surveyed = Surveyed;
    }

    public String getSurveyed() {
        return this.Surveyed;
    }

    public void setSite_Area(double Site_Area) {
        this.Site_Area = Site_Area;
    }

    public double getSite_Area() {
        return this.Site_Area;
    }

    public void setRelationship(String Relationship) {
        this.Relationship = Relationship;
    }

    public String getRelationship() {
        return this.Relationship;
    }

    public void setSHAPE_Length(double SHAPE_Length) {
        this.SHAPE_Length = SHAPE_Length;
    }

    public double getSHAPE_Length() {
        return this.SHAPE_Length;
    }

    public void setSHAPE_Area(double SHAPE_Area) {
        this.SHAPE_Area = SHAPE_Area;
    }

    public double getSHAPE_Area() {
        return this.SHAPE_Area;
    }
}

class PGeometry {
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

