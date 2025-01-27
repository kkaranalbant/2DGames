package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends BaseModel{

    private Texture duringShotTexture ;

    private Float shotSpeed ;

    private Float defaultShotSpeed ;

    private boolean isShooting ;

    public Weapon(Integer x, Integer y, Texture texture, Integer height, Integer width, Texture duringShotTexture , Float shotSpeed) {
        super(x, y, texture, height, width, true);
        this.duringShotTexture = duringShotTexture;
        this.shotSpeed = shotSpeed;
        defaultShotSpeed = shotSpeed ;
        isShooting = false ;
    }

    public Texture getDuringShotTexture() {
        return duringShotTexture;
    }

    public void setDuringShotTexture(Texture duringShotTexture) {
        this.duringShotTexture = duringShotTexture;
    }

    public Float getShotSpeed() {
        return shotSpeed;
    }

    public void setShotSpeed(Float shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public Float getDefaultShotSpeed() {
        return defaultShotSpeed;
    }
}
