package com.kaan.savehostiles.service;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kaan.savehostiles.model.Person;
import com.kaan.savehostiles.model.SpeedTreasure;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.model.Treasure;
import com.kaan.savehostiles.model.Weapon;
import com.kaan.savehostiles.model.WeaponTreasure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class WeaponTreasureService extends TreasureService{

    private static WeaponTreasureService weaponTreasureService ;


    private WeaponTreasureService() {
        setTreasures(new LinkedList<>());
    }

    public static WeaponTreasureService getInstance() {
        if (weaponTreasureService == null) {
            weaponTreasureService = new WeaponTreasureService() ;
        }
        return weaponTreasureService ;
    }

    @Override
    public void createRandomTreasure() {
        Treasure treasure = new WeaponTreasure(0 , 0 , null , 30 , 30 , null  , null , WeaponService.getInstance().getDefaultWeapon()) ;
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
        initTreasure((treasure));
        getTreasures().add(treasure) ;
    }

    protected void initTreasure (Treasure treasure) {
        int randNumber = getRandom().nextInt(4);
        while(randNumber == 0) {
            randNumber = getRandom().nextInt(4);
        }
        if (randNumber == 1) {
            treasure.setTexture(Textures.AK_47.getTexture());
            ((WeaponTreasure)treasure).setWeapon(WeaponService.getInstance().getWeapons().get(1));
        }
        else if (randNumber == 2) {
            treasure.setTexture(Textures.DEAGLE.getTexture());
            ((WeaponTreasure)treasure).setWeapon(WeaponService.getInstance().getWeapons().get(2));
        }
        else if (randNumber == 3) {
            treasure.setTexture(Textures.M4A4.getTexture());
            ((WeaponTreasure)treasure).setWeapon(WeaponService.getInstance().getWeapons().get(3));
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
        for (Treasure treasure : SpeedTreasureService.getInstance().getTreasures()) {
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
            if (treasure.isStarted() && treasure.isActive() && treasure.getFinishingTimeInMs() < System.currentTimeMillis()) {
                WeaponService.getInstance().setMainWeapon(((WeaponTreasure)treasure).getDefaultWeapon());
                treasure.setActive(false);
            }
            if (-treasure.getCreatingTimeInMs() + System.currentTimeMillis() > 15000) {
                treasureIterator.remove();
            }
        }
    }


}
