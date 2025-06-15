package com.example.pilifitproject.model;

public class Fit extends Entity{
    private int topId;
    private int bottomId;
    private int shoesId;
    private int is_Favorite;

    public Fit (int id, String name, int topId, int bottomId, int shoesId, int is_Favorite){
        super(id, name);
        this.topId = topId;
        this.bottomId = bottomId;
        this.shoesId = shoesId;
        this.is_Favorite = is_Favorite;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public int getTopId(){return topId;}
    public int getBottomId(){return bottomId;}
    public int getShoesId(){return shoesId;}
    public int getIs_Favorite(){return is_Favorite;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setTop(int topId){this.topId = topId;}
    public void setBottom(int bottomId){this.bottomId = bottomId;}
    public void setShoes(int shoesId){this.shoesId = shoesId;}
    public void setIs_Favorite(int is_Favorite){this.is_Favorite = is_Favorite;}





}

