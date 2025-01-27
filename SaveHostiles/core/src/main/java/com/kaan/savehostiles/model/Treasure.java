package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class Treasure extends BaseModel{

    private Long creatingTimeInMs ;

    private Long finishingTimeInMs ;

    private boolean isStarted ;

    public Treasure(Integer x, Integer y, Texture texture, Integer height, Integer width , Long finishingTimeInMs) {
        super(x, y, texture, height, width , false);
        this.creatingTimeInMs = System.currentTimeMillis();
        this.finishingTimeInMs = finishingTimeInMs;
        isStarted = false ;
    }

    public Long getCreatingTimeInMs() {
        return creatingTimeInMs;
    }

    public void setCreatingTimeInMs(Long creatingTimeInMs) {
        this.creatingTimeInMs = creatingTimeInMs;
    }

    public Long getFinishingTimeInMs() {
        return finishingTimeInMs;
    }

    public void setFinishingTimeInMs(Long finishingTimeInMs) {
        this.finishingTimeInMs = finishingTimeInMs;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
