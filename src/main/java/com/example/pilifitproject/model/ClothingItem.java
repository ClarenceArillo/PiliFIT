package com.example.pilifitproject.model;

public class ClothingItem {
    private int id;
    private String name;
    private String ImagePath;
    private int categoryId;
    private String color;
    private String style;
    private String size;
    private int isFavorite;

    public ClothingItem(int id, String name, String ImagePath, int categoryId, String color, String style, String size, int isFavorite){
        this.id = id;
        this.name = name;
        this.ImagePath = ImagePath;
        this.categoryId = categoryId;
        this.color = color;
        this.style = style;
        this.size = size;
        this.isFavorite = isFavorite;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getImagePath() {return ImagePath;}
    public int getCategoryId(){return categoryId;}
    public String getColor(){return color;}
    public String getStyle(){return style;}
    public String getSize(){return size;}
    public int getIsFavorite(){return isFavorite;}


    public void setId(int id){this.id= id;}
    public void setName(String name){this.name = name;}
    public void setImagePath(String ImagePath){this.ImagePath = ImagePath;}
    public void setCategoryId(int categoryId){this.categoryId = categoryId;}
    public void setColor(String color){this.color = color;}
    public void setStyle(String style){this.style = style;}
    public void setSize(String size){this.size = size;}
    public void setIsFavorite(int isFavorite){this.isFavorite = isFavorite;}

}

