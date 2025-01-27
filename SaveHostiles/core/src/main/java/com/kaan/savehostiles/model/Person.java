package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public class Person extends BaseModel{

    private Texture afterShootingTexture ;

    private Texture activeTexture ;

    private boolean isEnemy ;

    private Long creatingTimeInMs ;
    private Long notActiveWhenInMs ;

    public Person(Integer x, Integer y, Texture texture, Integer height, Integer width, Texture afterShootingTexture , boolean isEnemy) {
        super(x, y, texture, height, width , true);
        this.afterShootingTexture = afterShootingTexture;
        this.isEnemy = isEnemy;
        activeTexture = texture ;
        creatingTimeInMs = System.currentTimeMillis() ;
    }

    public Texture getAfterShootingTexture() {
        return afterShootingTexture;
    }

    public void setAfterShootingTexture(Texture afterShootingTexture) {
        this.afterShootingTexture = afterShootingTexture;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public Long getNotActiveWhenInMs() {
        return notActiveWhenInMs;
    }

    public void setNotActiveWhenInMs(Long notActiveWhenInMs) {
        this.notActiveWhenInMs = notActiveWhenInMs;
    }

    public Texture getActiveTexture() {
        return activeTexture;
    }

    public void setActiveTexture(Texture activeTexture) {
        this.activeTexture = activeTexture;
    }

    public Long getCreatingTimeInMs() {
        return creatingTimeInMs;
    }
}
