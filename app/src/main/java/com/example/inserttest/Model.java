package com.example.inserttest;


public class Model {
    String name;
    String image;
    String specification;
    String latitude;
    String longitude;
    String description;
    int visited;
    String view;
    String rating;
    String pname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    String heading;
    String comment;

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String howtoreach;
    public Model(String name,String specification,String image){
        this.name = name;
        this.specification = specification;
        this.image = image;
    }
    public Model(){}
    public Model(String name,String specification,String image,String latitude,String longitude,String description){
        this.name = name;
        this.specification = specification;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Model(String name,String specification,String image,String latitude,String longitude,String description,String view,String howtoreach){
        this.name = name;
        this.specification = specification;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.view = view;
        this.howtoreach = howtoreach;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getHowtoreach() {
        return howtoreach;
    }

    public void setHowtoreach(String howtoreach) {
        this.howtoreach = howtoreach;
    }
}