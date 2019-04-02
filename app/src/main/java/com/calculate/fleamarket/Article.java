package com.calculate.fleamarket;

import android.content.Intent;
import android.view.View;

import java.io.Serializable;

public class Article implements Serializable {
    int ID;
    String description;
    double price;
    String username;
    String email;
    String telephone;
    double latitude;
    double longitude;

    public Article(int ID, String description, double price, String username, String email, String telephone, double latitude, double longitude) {
        this.ID = ID;
        this.description = description;
        this.price = price;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String toString(){
        return description + "   |   " + price;
    }

}
