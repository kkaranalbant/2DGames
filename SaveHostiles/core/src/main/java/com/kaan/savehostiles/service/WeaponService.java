package com.kaan.savehostiles.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kaan.savehostiles.SaveHostiles;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.model.Weapon ;

import java.util.ArrayList;
import java.util.List ;

public class WeaponService {

    private static WeaponService weaponService ;

    private List<Weapon> weapons ;

    private Weapon mainWeapon ;

    private Weapon defaultWeapon ;

    private WeaponService() {
        this.weapons = new ArrayList<>() ;
        weapons.add(new Weapon(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/2, Textures.GLOCK.getTexture(),20,20,Textures.GLOCK_SHOOTING.getTexture(), 300F)) ;
        weapons.add(new Weapon(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/2, Textures.AK_47.getTexture(),20,20,Textures.AK_47_SHOOTING.getTexture(), 400F)) ;
        weapons.add(new Weapon(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/2, Textures.DEAGLE.getTexture(),20,20,Textures.DEAGLE_SHOOTING.getTexture(), 350F)) ;
        weapons.add(new Weapon(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/2, Textures.M4A4.getTexture(),20,20,Textures.M4A4_SHOOTING.getTexture(), 450F)) ;
        mainWeapon = weapons.get(0) ;
        defaultWeapon = mainWeapon ;
    }

    public static WeaponService getInstance() {
        if (weaponService == null) {
            weaponService = new WeaponService() ;
        }
        return weaponService ;
    }

    public void updateGunPosition (Integer y) {
        if (!(Gdx.graphics.getHeight() <= y)) {
            mainWeapon.setY(y);
        }
    }

    public void swapGunTexture () {
        if (!mainWeapon.isShooting()) {
            SaveHostiles.getBatch().draw(mainWeapon.getTexture() , mainWeapon.getX() , mainWeapon.getY() , mainWeapon.getWidth() , mainWeapon.getHeight());
        }
        else {
            SaveHostiles.getBatch().draw(mainWeapon.getDuringShotTexture(),mainWeapon.getX(),mainWeapon.getY() , mainWeapon.getWidth() , mainWeapon.getHeight());
        }
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    public void setMainWeapon(Weapon mainWeapon) {
        this.mainWeapon = mainWeapon;
    }

    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }
}
