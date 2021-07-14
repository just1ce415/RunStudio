package com.roflanRun.CulComf;

public class Boot {
    private int _id;
    private int pronation;
    private int rw;
    private String gender;
    private String name;
    private String purpose;
    private float weight;
    private String description;
    private String images;


    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setPronation(int pronation) {
        this.pronation = pronation;
    }

    public void setRw(int rw) {
        this.rw = rw;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setImages(String images){
        this.images = images;
    }

    public Boot(int _id, String gender, String name, int pronation, int rw, String purpose, float weight, String description, String images) {
        this._id = _id;
        this.pronation = pronation;
        this.rw = rw;
        this.gender = gender;
        this.name = name;
        this.purpose = purpose;
        this.weight = weight;
        this.description = description;
        this.images = images;
    }

    public Boot(){

    }

    public int get_id() {
        return _id;
    }

    public int getPronation() {
        return pronation;
    }

    public int getRw() {
        return rw;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public float getWeight(){
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getImages(){
        return images;
    }
}
