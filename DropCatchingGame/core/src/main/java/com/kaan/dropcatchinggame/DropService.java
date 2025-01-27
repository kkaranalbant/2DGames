package com.kaan.dropcatchinggame;

import java.util.Random;

public class DropService {

    private static int decreasingYAmount ;
    private static int xOrigin ;
    private static int xBound ;

    private static int defaultY ;

    private Random random ;

    public DropService () {
        decreasingYAmount = 20 ;
        xOrigin = 1000 ;
        xBound = 3000 ;
        defaultY = 800 ;
        random = new Random () ;
    }

    public int getRandomXValueOfDrop () {
        boolean found = false ;
        int randomXPosition = 0 ;
        while (!found) {
            randomXPosition = random.nextInt(xOrigin) ;
            if (randomXPosition <= xBound) {
                found = true ;
            }
        }
        return randomXPosition ;
    }

}
