package com.kaan.savehostiles.model;

import com.badlogic.gdx.graphics.Texture;

public class WeaponTreasure extends Treasure{

    private Weapon weapon ;

    private Weapon defaultWeapon ;

    public WeaponTreasure(Integer x, Integer y, Texture texture, Integer height, Integer width, Long finishingTimeInMs, Weapon weapon , Weapon defaultWeapon) {
        super(x, y, texture, height, width, finishingTimeInMs);
        this.weapon = weapon;
        this.defaultWeapon = defaultWeapon ;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }

    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }
}
