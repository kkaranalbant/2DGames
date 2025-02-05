package com.kaan.savehostiles.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kaan.savehostiles.SaveHostiles;
import com.kaan.savehostiles.model.BaseModel;
import com.kaan.savehostiles.model.Bullet;
import com.kaan.savehostiles.model.Person;
import com.kaan.savehostiles.model.SpeedTreasure;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.model.Treasure;
import com.kaan.savehostiles.model.Weapon;
import com.kaan.savehostiles.model.WeaponTreasure;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BulletService {

    private List<Bullet> bullets ;

    private SpeedTreasureService speedTreasureService ;

    private WeaponTreasureService weaponTreasureService ;

    private WeaponService weaponService ;

    private PersonService personService ;

    private static BulletService bulletService ;

    public BulletService () {
        bullets = new LinkedList<>() ;
        speedTreasureService = SpeedTreasureService.getInstance() ;
        weaponTreasureService = WeaponTreasureService.getInstance() ;
        weaponService = WeaponService.getInstance() ;
    }

    public static BulletService getInstance() {
        if (bulletService == null) {
            bulletService = new BulletService() ;
        }
        return bulletService ;
    }

    public void updateBullets () {
        Iterator<Bullet> bulletIterator = bullets.iterator() ;
        while(bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next() ;
            if (bullet.isActive()) {
                float newX = bullet.getX() +  WeaponService.getInstance().getMainWeapon().getShotSpeed() * Gdx.graphics.getDeltaTime() ;
                bullet.setX((int)newX);
                if (bullet.getX() >= Gdx.graphics.getWidth()) {
                    bulletIterator.remove();
                }
            }
            else {
                bulletIterator.remove();
            }
        }
    }

    public void createBullet () {
        Bullet bullet = new Bullet(WeaponService.getInstance().getMainWeapon().getX() , WeaponService.getInstance().getMainWeapon().getY() , Textures.BULLET.getTexture(), 30,30) ;
        bullets.add(bullet) ;
        WeaponService.getInstance().getMainWeapon().setShooting(true);
    }

    public void drawBullets () {
        for (Bullet bullet : bullets) {
            SaveHostiles.getBatch().draw(bullet.getTexture() , bullet.getX() , bullet.getY() , bullet.getWidth() , bullet.getHeight()) ;
        }
    }

    public void bulletTreasureControl () {
        List<Treasure> weaponTreasures = weaponTreasureService.getTreasures() ;
        List<Treasure> speedTreasures = speedTreasureService.getTreasures() ;
        for (Bullet bullet : bullets) {
            if (bullet.isActive()) {
                for (Treasure treasure : weaponTreasures) {
                    Rectangle bulletRect = new Rectangle(bullet.getX() , bullet.getY() , bullet.getWidth() , bullet.getHeight()) ;
                    Rectangle treasureRect = new Rectangle(treasure.getX(),treasure.getY() , treasure.getWidth() , treasure.getHeight()) ;
                    if (Intersector.overlaps(bulletRect , treasureRect) && !treasure.isActive() && !treasure.isStarted()) {
                        weaponService.setMainWeapon(((WeaponTreasure) treasure).getWeapon());
                        bullet.setActive(false);
                        weaponTreasureService.startTreasure(treasure);
                    }
                }
                for (Treasure treasure : speedTreasures) {
                    Rectangle bulletRect = new Rectangle(bullet.getX() , bullet.getY() , bullet.getWidth() , bullet.getHeight()) ;
                    Rectangle treasureRect = new Rectangle(treasure.getX(),treasure.getY() , treasure.getWidth() , treasure.getHeight()) ;
                    if (Intersector.overlaps(bulletRect , treasureRect) && !treasure.isStarted()) {
                        weaponService.getMainWeapon().setShotSpeed(weaponService.getMainWeapon().getDefaultShotSpeed() * ((SpeedTreasure) treasure).getIncreasingRatio());
                        bullet.setActive(false);
                        speedTreasureService.startTreasure(treasure);
                    }
                }
            }
        }
    }

    public void bulletPersonControl () {
        personService = PersonService.getInstance() ;
        List<Person> persons = personService.getPersons() ;
        for (Bullet bullet : bullets) {
            if (bullet.isActive()) {
                for (Person person : persons) {
                    if (person.isActive()) {
                        Rectangle bulletRect = new Rectangle(bullet.getX() , bullet.getY() , bullet.getWidth() , bullet.getHeight()) ;
                        Rectangle personRect = new Rectangle(person.getX(),person.getY() , person.getWidth() , person.getHeight()) ;
                        if (Intersector.overlaps(bulletRect , personRect)) {
                            if (person.isEnemy()) {
                                SaveHostiles.setScore(SaveHostiles.getScore() + PersonService.getTerroristShootingPoint());
                            }
                            else {
                                SaveHostiles.setScore(SaveHostiles.getScore() + PersonService.getHostileShootingPoint());
                            }
                            person.setActive(false);
                            person.setNotActiveWhenInMs(System.currentTimeMillis());
                            person.setActiveTexture(person.getAfterShootingTexture());
                            bullet.setActive(false);
                        }
                    }
                }
            }
        }
    }

    /*private boolean hasCollision (BaseModel bullet , BaseModel baseModel) {
        if (bullet.getY() < baseModel.getY()) {
            if (Math.abs(bullet.getY() - baseModel.getY()) < bullet.getHeight() && bullet.getX()+bullet.getWidth() < baseModel.getX()) {
                return true ;
            }
            return false ;
        }
        else {
            if (Math.abs(baseModel.getY() + baseModel.getHeight() - bullet.getY()) < baseModel.getHeight()&& bullet.getX()+bullet.getWidth() < baseModel.getX()) {
                return true ;
            }
            return false ;
        }
    }*/



    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }
}
