package com.example.pilifitproject.model;

public abstract class Entity {
    protected int id;
    protected String name;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Original getters/setters from child classes
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
