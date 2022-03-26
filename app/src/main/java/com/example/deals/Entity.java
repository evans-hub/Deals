package com.example.deals;

public class Entity {

    private String image,title,location,ratings,pay;


    public Entity() {
    }

    public Entity(String image, String title, String location, String ratings, String pay) {
        this.image = image;
        this.title = title;
        this.location = location;
        this.ratings = ratings;
        this.pay = pay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
