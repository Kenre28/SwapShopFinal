package com.example.swapshop2;

public class Product {
    public String name, description, location, UID;

    public Product(){

    }

    public Product(String name,String description, String location, String UID){
        this.name = name;
        this.description = description;
        this.location = location;
        this.UID = UID;
    }
}
