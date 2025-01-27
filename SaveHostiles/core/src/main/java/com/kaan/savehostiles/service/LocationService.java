package com.kaan.savehostiles.service;

import com.badlogic.gdx.Gdx;
import com.kaan.savehostiles.model.BaseModel;

import java.util.Random;

public class LocationService {

    private static Random random ;

    static {
        random = new Random () ;
    }

    public static int getRandomXPosition (BaseModel baseModel) {
        int result = random.nextInt(Gdx.graphics.getWidth() - baseModel.getWidth()) ;
        while (result <= Gdx.graphics.getWidth() / 10 + 40) {
            result = random.nextInt(Gdx.graphics.getWidth() - baseModel.getWidth()) ;
        }
        return  result;
    }

    public  static int getRandomYPosition (BaseModel baseModel) {
        return random.nextInt(Gdx.graphics.getHeight() - baseModel.getHeight()) ;
    }


}
