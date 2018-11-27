package ca.bcit.fitmeet.dashboard.model;

public class SFProperties extends Property {
    private String TYPE;

    private String name;

    private String imageFileName;

    private String ACTIVITIES;

    private String X;

    private String Y;

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setACTIVITIES(String ACTIVITIES) {
        this.ACTIVITIES = ACTIVITIES;
    }

    public String getACTIVITIES() {
        return this.ACTIVITIES;
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

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}

