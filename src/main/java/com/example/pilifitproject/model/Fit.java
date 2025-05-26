package com.example.pilifitproject.model;

public class Fit {
    private int id;
    private String name;
    private ClothingItem headwear;
    private ClothingItem top;
    private ClothingItem bottom;
    private ClothingItem shoes;
    private ClothingItem accessories;
    private int is_Favorite;

    public Fit (int id, String name, ClothingItem headwear, ClothingItem top, ClothingItem bottom, ClothingItem shoes, ClothingItem accessories, int is_Favorite){
        this.id = id;
        this.name = name;
        this.headwear = headwear;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.accessories = accessories;
        this.is_Favorite = is_Favorite;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public ClothingItem getHeadwear(){return headwear;}
    public ClothingItem getTop(){return top;}
    public ClothingItem getBottom(){return bottom;}
    public ClothingItem getShoes(){return shoes;}
    public ClothingItem getAccessories(){return accessories;}
    public int getIs_Favorite(){return is_Favorite;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setHeadwear(ClothingItem headwear){this.headwear = headwear;}
    public void setTop(ClothingItem top){this.top = top;}
    public void setBottom(ClothingItem bottom){this.bottom = bottom;}
    public void setShoes(ClothingItem shoes){this.shoes = shoes;}
    public void setAccessories(ClothingItem accessories){this.accessories = accessories;}
    public void setIs_Favorite(int is_Favorite){this.is_Favorite = is_Favorite;}





}

