package ca.bcit.fitmeet.dashboard.model;

import java.util.List;

public class CulturalEvents {
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

class CEProperties {
    private String email;

    private String phone;

    private String Name;

    private String Address;

    private String Descriptn;

    private String id;

    private int category;

    private String company;

    private String address2;

    private String city;

    private String prov;

    private String pcode;

    private String fax;

    private String website;

    private String social_networks;

    private String summary;

    private String catname;

    private String X;

    private String Y;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setDescriptn(String Descriptn) {
        this.Descriptn = Descriptn;
    }

    public String getDescriptn() {
        return this.Descriptn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getProv() {
        return this.prov;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcode() {
        return this.pcode;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return this.fax;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setSocial_networks(String social_networks) {
        this.social_networks = social_networks;
    }

    public String getSocial_networks() {
        return this.social_networks;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatname() {
        return this.catname;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getX() {
        return this.X;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

    public String getY() {
        return this.Y;
    }
}

class CEFeatures {
    private String type;

    private CEGeometry geometry;

    private CEProperties properties;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setGeometry(CEGeometry geometry) {
        this.geometry = geometry;
    }

    public CEGeometry getGeometry() {
        return this.geometry;
    }

    public void setProperties(CEProperties properties) {
        this.properties = properties;
    }

    public CEProperties getProperties() {
        return this.properties;
    }
}