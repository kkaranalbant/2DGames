package com.kaan.savehostiles.service;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kaan.savehostiles.model.Person;
import com.kaan.savehostiles.model.SpeedTreasure;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.model.Treasure;

import java.util.Iterator;
import java.util.LinkedList;

public class SpeedTreasureService extends TreasureService{

    private static SpeedTreasureService speedTreasureService ;

    private SpeedTreasureService() {
        setTreasures(new LinkedList<>());
    }

    public static SpeedTreasureService getInstance () {
        if (speedTreasureService == null) {
            speedTreasureService = new SpeedTreasureService() ;
        }
        return speedTreasureService ;
    }

    @Override
    public void createRandomTreasure() {
        Treasure treasure = new SpeedTreasure(0 , 0 , null , 30 , 30  , null , null) ;
        treasure.setX(LocationService.getRandomXPosition(treasure));
        treasure.setY(LocationService.getRandomYPosition(treasure)) ;
        Rectangle rectangle = new Rectangle(treasure.getX(),treasure.getY(),treasure.getWidth(),treasure.getHeight()) ;
        while (isOverlaps(rectangle)) {
            rectangle.setX(LocationService.getRandomXPosition(treasure)) ;
            rectangle.setY(LocationService.getRandomYPosition(treasure)) ;
        }
        treasure.setX((int)rectangle.getX()) ;
        treasure.setY((int)rectangle.getY());
        treasure.setCreatingTimeInMs(System.currentTimeMillis());
        treasure.setFinishingTimeInMs(0L);
        initTreasure(treasure);
        getTreasures().add(treasure) ;
    }


    protected void initTreasure (Treasure treasure) {
        int randomNumber = getRandom().nextInt(4) ;
        while(randomNumber == 0) {
            randomNumber = getRandom().nextInt(4);
        }
        if (randomNumber == 1) {
            treasure.setTexture(Textures.SPEED_1_5.getTexture());
            ((SpeedTreasure)treasure).setIncreasingRatio(1.5F);
        }
        else if (randomNumber == 2) {
            treasure.setTexture(Textures.SPEED_2.getTexture());
            ((SpeedTreasure)treasure).setIncreasingRatio(2F);
        }
        else if (randomNumber == 3) {
            treasure.setTexture(Textures.SPEED_3.getTexture());
            ((SpeedTreasure)treasure).setIncreasingRatio(3.5F);
        }
    }


    private boolean isOverlaps (Rectangle rectangle) {
        for (Treasure treasure : getTreasures()) {
            if(!treasure.isStarted() && !treasure.isActive()) {
                if (Intersector.overlaps(rectangle , new Rectangle(treasure.getX(),treasure.getY(),treasure.getWidth(),treasure.getHeight()))) {
                    return true ;
                }
            }
        }
        for (Treasure treasure : WeaponTreasureService.getInstance().getTreasures()) {
            if(!treasure.isStarted() && !treasure.isActive()) {
                if (Intersector.overlaps(rectangle , new Rectangle(treasure.getX(),treasure.getY(),treasure.getWidth(),treasure.getHeight()))) {
                    return true ;
                }
            }
        }
        for (Person person : PersonService.getInstance().getPersons()) {
            if (person.isActive()) {
                if (Intersector.overlaps(rectangle , new Rectangle(person.getX(), person.getY(), person.getWidth(),  person.getHeight()))) {
                    return true ;
                }
            }
        }
        return false ;
    }



    public void checkActiveTreasureTimes () {
        Iterator<Treasure> treasureIterator = getTreasures().iterator() ;
        while (treasureIterator.hasNext()) {
            Treasure treasure = treasureIterator.next() ;
            if (treasure.isActive() && treasure.getFinishingTimeInMs() < System.currentTimeMillis()) {
                WeaponService.getInstance().getMainWeapon().setShotSpeed(WeaponService.getInstance().getMainWeapon().getDefaultShotSpeed());
                treasure.setActive(false);
            }
            if (-treasure.getCreatingTimeInMs() + System.currentTimeMillis() > 15000) {
                treasureIterator.remove();
            }
        }
    }




}
