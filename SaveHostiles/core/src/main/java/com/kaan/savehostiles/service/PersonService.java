package com.kaan.savehostiles.service;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kaan.savehostiles.SaveHostiles;
import com.kaan.savehostiles.model.BaseModel;
import com.kaan.savehostiles.model.Bullet;
import com.kaan.savehostiles.model.Person;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.model.Treasure;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PersonService {

    private static PersonService personService ;
    private static final Integer HOSTILE_SHOOTING_POINT ;

    private static final Integer  TERRORIST_SHOOTING_POINT;

    private List<Person> persons ;

    private final BulletService bulletService ;

    private final TreasureService speedTreasureService ;

    private final TreasureService weaponTreasureService ;

    private final Random random ;

    static {
        HOSTILE_SHOOTING_POINT = -30 ;

        TERRORIST_SHOOTING_POINT = 20 ;
    }


    private PersonService() {
        this.persons = new LinkedList<>();

        speedTreasureService = SpeedTreasureService.getInstance() ;

        weaponTreasureService = WeaponTreasureService.getInstance() ;

        bulletService = BulletService.getInstance() ;

        random = new Random() ;

    }

    public static PersonService getInstance() {
        if (personService == null) {
            personService = new PersonService() ;
        }
        return personService ;


    }

    public void createRandomPerson () {
        boolean isHostile = random.nextBoolean() ;
        if (isHostile) {
            createHostile();
        }
        else {
            createTerrorist();
        }
    }

    private void createHostile ()  {
        Person person = new Person(0 , 0 , Textures.HOSTILE.getTexture(), 40 , 40 , Textures.HOSTILE_AFTER_SHOT.getTexture(), false) ;
        Rectangle rectangle = new Rectangle(person.getX() , person.getY() , person.getWidth() ,person.getHeight()) ;
        while (isOverlapping(rectangle)) {
            rectangle.setX(LocationService.getRandomXPosition(person)) ;
            rectangle.setY(LocationService.getRandomYPosition(person)) ;
        }
        person.setX((int)rectangle.getX()) ;
        person.setY((int)rectangle.getY()) ;
        persons.add(person) ;
    }

    private void createTerrorist () {
        Person person = new Person(0 , 0 , Textures.TERRORIST.getTexture(), 40 , 40 , Textures.TERRORIST_AFTER_SHOT.getTexture() , true) ;
        Rectangle rectangle = new Rectangle(person.getX() , person.getY() , person.getWidth() ,person.getHeight()) ;
        while (isOverlapping(rectangle)) {
            rectangle.setX(LocationService.getRandomXPosition(person)) ;
            rectangle.setY(LocationService.getRandomYPosition(person)) ;
        }
        person.setX((int)rectangle.getX()) ;
        person.setY((int)rectangle.getY()) ;
        persons.add(person) ;
    }

    private boolean isOverlapping (Rectangle rectangle) {
        for (Treasure treasure : SpeedTreasureService.getInstance().getTreasures()) {
            Rectangle rectangle1 = new Rectangle(treasure.getX(),treasure.getY(),treasure.getWidth(),treasure.getHeight()) ;
            if (Intersector.overlaps(rectangle,rectangle1)) {
                return true ;
            }
        }

        for (Treasure treasure : WeaponTreasureService.getInstance().getTreasures()) {
            Rectangle rectangle1 = new Rectangle(treasure.getX(),treasure.getY(),treasure.getWidth(),treasure.getHeight()) ;
            if (Intersector.overlaps(rectangle,rectangle1)) {
                return true ;
            }
        }
        for (Person person : persons) {
            Rectangle rectangle1 = new Rectangle(person.getX(),person.getY(),person.getWidth(),person.getHeight()) ;
            if (Intersector.overlaps(rectangle,rectangle1)) {
                return true ;
            }
        }
        return false ;
    }


    public void drawPersons () {
        updatePersons();
        for (Person person : persons) {
            SaveHostiles.getBatch().draw(person.getActiveTexture() , person.getX() , person.getY() , person.getWidth() , person.getHeight());
        }
    }

    private void updatePersons () {
        Iterator<Person> personIterator = persons.iterator() ;
        while (personIterator.hasNext()) {
            Person person = personIterator.next() ;
            if (!person.isActive() && System.currentTimeMillis() - person.getNotActiveWhenInMs() >= 2000) {
                personIterator.remove();
            }
            else if (person.isActive() && System.currentTimeMillis() - person.getCreatingTimeInMs() >= 5000) {
                personIterator.remove();
            }
        }
    }


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public static Integer getHostileShootingPoint () {
        return HOSTILE_SHOOTING_POINT ;
    }

    public static Integer getTerroristShootingPoint () {
        return TERRORIST_SHOOTING_POINT ;
    }
}
