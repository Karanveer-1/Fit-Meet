package ca.bcit.fitmeet.dashboard.model;

public class PRandCSPProperties extends Property {
    private String Name;

    private String imageFileName;

    private String Description;

    private String Category;

    private String Hours;

    private String Location;

    private String PC;

    private String Phone;

    private String Email;

    private String Website;

    private String X;

    private String Y;

    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
    public String getDescription(){
        return this.Description;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getCategory(){
        return this.Category;
    }
    public void setHours(String Hours){
        this.Hours = Hours;
    }
    public String getHours(){
        return this.Hours;
    }
    public void setLocation(String Location){
        this.Location = Location;
    }
    public String getLocation(){
        return this.Location;
    }
    public void setPC(String PC){
        this.PC = PC;
    }
    public String getPC(){
        return this.PC;
    }
    public void setPhone(String Phone){
        this.Phone = Phone;
    }
    public String getPhone(){
        return this.Phone;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public String getEmail(){
        return this.Email;
    }
    public void setWebsite(String Website){
        this.Website = Website;
    }
    public String getWebsite(){
        return this.Website;
    }
    public void setX(String X){
        this.X = X;
    }
    public String getX(){
        return this.X;
    }
    public void setY(String Y){
        this.Y = Y;
    }
    public String getY(){
        return this.Y;
    }

    @Override
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
