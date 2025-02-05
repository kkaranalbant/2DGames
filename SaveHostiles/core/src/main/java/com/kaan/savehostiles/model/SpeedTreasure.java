package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public class SpeedTreasure extends Treasure{

    private Float increasingRatio;

    public SpeedTreasure(Integer x, Integer y, Texture texture, Integer height, Integer width, Long finishingTimeInMs, Float increasingRatio) {
        super(x, y, texture, height, width, finishingTimeInMs);
        this.increasingRatio = increasingRatio;
    }

    public Float getIncreasingRatio() {
        return increasingRatio;
    }

    public void setIncreasingRatio(Float increasingRatio) {
        this.increasingRatio = increasingRatio;
    }
}
