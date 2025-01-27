package com.kaan.balloonpopping;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;
import java.util.Iterator;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class BalloonPopping extends ApplicationAdapter {
    private SpriteBatch batch;

    private OrthographicCamera camera ;

    private Texture red ;

    private Texture blue ;

    private Texture black ;

    private Texture mix ;

    private Random random ;

    private Array<Balloon> balloons ;

    private Long score ;

    private Long lastBalloonCreatingTime ;

    private BitmapFont font ;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera() ;
        camera.setToOrtho(false , 800 , 480);
        red = new Texture(Gdx.files.internal("red.png")) ;
        black = new Texture(Gdx.files.internal("black.png")) ;
        blue = new Texture(Gdx.files.internal("blue.png")) ;
        mix = new Texture(Gdx.files.internal("mix.png")) ;
        random = new Random () ;
        score = 0L ;
        lastBalloonCreatingTime = TimeUtils.millis() ;
        balloons = new Array<>() ;
        font = new BitmapFont() ;
        font.setColor(1F , 0F , 0F , 1F);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (TimeUtils.millis() - lastBalloonCreatingTime >= 1000 && balloons.size < 10) {
            Rectangle rectangle = createRandomBalloonRectangle() ;
            Texture texture = getRandomBalloon() ;
            Balloon balloon = new Balloon() ;
            balloon.setRectangle(rectangle);
            balloon.setTexture(texture);
            balloons.add(balloon);
            lastBalloonCreatingTime = TimeUtils.millis() ;
            System.out.println("girdi");
        }


        for (Balloon balloon : balloons) {
            batch.draw(balloon.getTexture(),balloon.getRectangle().x,balloon.getRectangle().y);
        }

        if(Gdx.input.isTouched()) {
            Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY() , 0);  // Y ekseninde dönüşüm
            camera.unproject(vector);
            Iterator<Balloon> balloonIterator = balloons.iterator() ;
            while(balloonIterator.hasNext()) {
                Balloon balloon = balloonIterator.next() ;
                if(Math.abs(balloon.getRectangle().x - vector.x) < balloon.getRectangle().width && Math.abs(balloon.getRectangle().y - vector.y) < balloon.getRectangle().height) {
                    if (balloon.getTexture() == mix) {
                        score -= 30 ;
                    }
                    else {
                        score += 20 ;
                    }
                    balloonIterator.remove();
                }
            }
        }

        font.draw(batch,"Score : "+score,10,470 ) ;

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private Texture getRandomBalloon () {
        int choice = random.nextInt(5) ;
        if (choice == 1) {
            return red ;
        }
        else if (choice == 2) {
            return blue ;
        }
        else if (choice == 3) {
            return black ;
        }
        else {
            return mix ;
        }
    }

    private Rectangle createRandomBalloonRectangle () {
        Rectangle rectangle = new Rectangle() ;
        rectangle.height = 64 ;
        rectangle.width = 64 ;
        rectangle.x = getRandomAxisComponentPixel(800) ;
        rectangle.y = getRandomAxisComponentPixel(480) ;
        boolean isValidPosition = false ;
        while(!isValidPosition) {
            if (isValidPosition(rectangle)) {
                isValidPosition = true ;
            }
            else {
                rectangle.x = getRandomAxisComponentPixel(800) ;
                rectangle.y = getRandomAxisComponentPixel(480) ;
            }
        }
        return rectangle ;
    }

    private boolean isValidPosition(Rectangle rectangle) {
        for (Balloon balloon : balloons) {
            if (rectangle.overlaps(balloon.getRectangle())) {
                return false;
            }
        }
        return true;
    }

    private int getRandomAxisComponentPixel (int bound) {
        return random.nextInt(bound) ;
    }
}
