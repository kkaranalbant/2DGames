package com.kaan.movingman.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kaan.movingman.model.Obstacle;
import com.kaan.movingman.model.Textures;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class ObstacleService {

    private List<Obstacle> obstacles;

    public ObstacleService() {
        this.obstacles = Collections.synchronizedList(new LinkedList<>());
    }

    public boolean hasCollision(int charX, int charY) {
        for (Obstacle obstacle : obstacles) {
            Rectangle charRectangle = new Rectangle(charX, charY, 64, 64);
            Rectangle obstacleRectangle = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
            if (Intersector.overlaps(charRectangle, obstacleRectangle)) {
                return true;
            }
        }
        return false;
    }

    public void updateObstacles(int charX, AtomicLong score) {
        synchronized (obstacles) {
            Iterator<Obstacle> obstacleIterator = obstacles.iterator();
            while (obstacleIterator.hasNext()) {
                Obstacle obstacle = obstacleIterator.next();
                if (obstacle.getX() + obstacle.getWidth() <= charX) {
                    obstacleIterator.remove();
                    score.set(score.get()+30L) ;
                } else {
                    obstacle.setX((int) (obstacle.getX() - (200 * Gdx.graphics.getDeltaTime())));
                }
            }
        }
    }

    public void createObstacle() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Obstacle obstacle = new Obstacle();
                        obstacle.setHeight(50);
                        obstacle.setWidth(25);
                        obstacle.setX(1350);
                        obstacle.setY(100);
                        obstacles.add(obstacle);
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void drawObstacles(SpriteBatch batch) {
        synchronized (obstacles) {
            for (Obstacle obstacle : obstacles) {
                batch.draw(Textures.OBSTACLE.getTexture(), obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
            }
        }
    }
}
