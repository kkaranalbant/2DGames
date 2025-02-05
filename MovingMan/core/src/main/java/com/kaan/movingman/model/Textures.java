package com.kaan.movingman.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Textures {

    SUN,

    JUMP,

    CURVE,

    OBSTACLE,

    RUN;

    private Texture texture;


    private Textures() {
        init();
    }

    public Texture getTexture() {
        return texture;
    }

    private void init() {
        if (this.name().equals("SUN")) {
            texture = new Texture(Gdx.files.internal("sun.png"));
        } else if (this.name().equals("JUMP")) {
            texture = new Texture(Gdx.files.internal("jumpspritesheet.png"));
        } else if (this.name().equals("CURVE")) {
            texture = new Texture(Gdx.files.internal("curve.png"));
        } else if (this.name().equals("OBSTACLE")) {
            texture = new Texture(Gdx.files.internal("kaktus.png"));
        } else {
            texture = new Texture(Gdx.files.internal("runspritesheet.png"));
        }
    }
}
