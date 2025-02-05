package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class BaseModel {

    private Integer x ;

    private Integer y ;

    private Texture texture ;

    private Integer height ;

    private Integer width ;

    private boolean isActive ;

    public BaseModel(Integer x, Integer y, Texture texture, Integer height, Integer width , boolean isActive) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.height = height;
        this.width = width;
        this.isActive = isActive ;
    }

    public Integer getX() {
        return x;
    }


    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
