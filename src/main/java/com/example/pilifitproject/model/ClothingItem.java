package com.example.pilifitproject.model;

import java.util.Objects;

public class ClothingItem {
    private int id;
    private String name;
    private byte[] imageData;
    private int categoryId;
    private int colorId;
    private int styleId;
    private String size;
    private int isFavorite;

    public ClothingItem(int id, String name, byte[] imageData, int categoryId, int colorId, int styleId, String size, int isFavorite){
        this.id = id;
        this.name = name;
        this.imageData = imageData;
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.styleId = styleId;
        this.size = size;
        this.isFavorite = isFavorite;
    }

    // In ClothingItem.java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingItem that = (ClothingItem) o;
        return id == that.id; // Compare by ID only
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public byte[] getImageData() {return imageData;}
    public int getCategoryId(){return categoryId;}
    public int getColorId(){return colorId;}
    public int getStyleId(){return styleId;}
    public String getSize(){return size;}
    public int getIsFavorite(){return isFavorite;}


    public void setId(int id){this.id= id;}
    public void setName(String name){this.name = name;}
    public void setImageData(byte[] imageData){this.imageData = imageData;}
    public void setCategoryId(int categoryId){this.categoryId = categoryId;}
    public void setColorId(int colorId){this.colorId = colorId;}
    public void setStyleId(int styleId){this.styleId = styleId;}
    public void setSize(String size){this.size = size;}
    public void setIsFavorite(int isFavorite){this.isFavorite = isFavorite;}

}

