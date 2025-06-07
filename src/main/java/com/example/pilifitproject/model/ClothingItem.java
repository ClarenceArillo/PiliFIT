package com.example.pilifitproject.model;

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

