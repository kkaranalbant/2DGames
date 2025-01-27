package com.kaan.savehostiles.service;

import com.badlogic.gdx.graphics.Texture;
import com.kaan.savehostiles.SaveHostiles;
import com.kaan.savehostiles.model.Treasure;
import com.kaan.savehostiles.model.Weapon;
import com.kaan.savehostiles.model.WeaponTreasure;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class TreasureService {

    private List<Treasure> treasures ;

    private final Random random ;

    public TreasureService () {
        random = new Random() ;
    }


    public abstract void createRandomTreasure() ;

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public abstract  void checkActiveTreasureTimes () ;

    public void startTreasure(Treasure treasure) {
        treasure.setActive(true);
        treasure.setFinishingTimeInMs(System.currentTimeMillis() + 15000);
        treasure.setStarted(true);
    }

    public void drawTreasures () {
        for (Treasure treasure : treasures) {
            if(System.currentTimeMillis() - treasure.getCreatingTimeInMs() >= 11000) continue ;
            if (!treasure.isActive() && !treasure.isStarted()) {
                SaveHostiles.getBatch().draw(treasure.getTexture() , treasure.getX() , treasure.getY() , treasure.getWidth() , treasure.getHeight());
            }
        }
    }

    protected abstract void initTreasure (Treasure treasure) ;

    public Random getRandom() {
        return random;
    }
}
