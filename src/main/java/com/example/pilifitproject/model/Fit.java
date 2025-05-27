package com.example.pilifitproject.model;

public class Fit {
    private int id;
    private String name;
    private ClothingItem top;
    private ClothingItem bottom;
    private ClothingItem shoes;
    private int is_Favorite;

    public Fit (int id, String name, ClothingItem top, ClothingItem bottom, ClothingItem shoes, int is_Favorite){
        this.id = id;
        this.name = name;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.is_Favorite = is_Favorite;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public ClothingItem getTop(){return top;}
    public ClothingItem getBottom(){return bottom;}
    public ClothingItem getShoes(){return shoes;}
    public int getIs_Favorite(){return is_Favorite;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setTop(ClothingItem top){this.top = top;}
    public void setBottom(ClothingItem bottom){this.bottom = bottom;}
    public void setShoes(ClothingItem shoes){this.shoes = shoes;}
    public void setIs_Favorite(int is_Favorite){this.is_Favorite = is_Favorite;}





}

