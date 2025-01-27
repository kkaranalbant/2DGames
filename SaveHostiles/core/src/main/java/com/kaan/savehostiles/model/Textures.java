package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public enum Textures {

    TERRORIST ,

    TERRORIST_AFTER_SHOT ,

    HOSTILE ,

    HOSTILE_AFTER_SHOT ,

    AK_47 ,

    AK_47_SHOOTING ,

    M4A4 ,

    M4A4_SHOOTING ,

    DEAGLE ,

    DEAGLE_SHOOTING ,

    GLOCK ,

    GLOCK_SHOOTING ,

    BULLET ,

    SPEED_1_5 ,

    SPEED_2,

    SPEED_3 ;

    private Texture texture ;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
