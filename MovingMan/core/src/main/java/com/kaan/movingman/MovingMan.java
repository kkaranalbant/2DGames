package com.kaan.movingman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kaan.movingman.model.Textures;
import com.kaan.movingman.service.ObstacleService;

import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class MovingMan extends ApplicationAdapter {
    private SpriteBatch batch;

    private OrthographicCamera camera;

    private BitmapFont font;

    private Texture runSheet;

    private Texture jumpSheet;

    private Animation<TextureRegion> runAnimation;

    private Animation<TextureRegion> jumpAnimation;

    private Animation<TextureRegion> currentAnimation;

    private float stateTime;

    private TextureRegion currentFrame;

    private boolean isRunning;

    private ObstacleService obstacleService ;

    private AtomicLong score ;

    private boolean isGameOver ;


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1400, 800);
        font = new BitmapFont();
        runSheet = Textures.RUN.getTexture();
        jumpSheet = Textures.JUMP.getTexture();
        TextureRegion[][] runFrames = TextureRegion.split(runSheet, 64, 64);
        TextureRegion[] firstRowOfRunFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            firstRowOfRunFrames[i] = runFrames[0][i];
        }
        runAnimation = new Animation<>(0.1F, firstRowOfRunFrames);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion[][] jumpFrames = TextureRegion.split(jumpSheet, 64, 64);
        TextureRegion[] firstRowOfJumpFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            firstRowOfJumpFrames[i] = jumpFrames[0][i];
        }
        jumpAnimation = new Animation<>(0.1f, firstRowOfJumpFrames);
        jumpAnimation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0F;
        isRunning = true;
        currentAnimation = runAnimation ;
        obstacleService = new ObstacleService() ;
        obstacleService.createObstacle();
        score = new AtomicLong(0L) ;
        isGameOver = false ;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        if (isGameOver && Gdx.input.justTouched()) {
            isGameOver = false ;
            score.set(0L); ;
        }
        stateTime += Gdx.graphics.getDeltaTime();
        if (isRunning && stateTime == 3 * Gdx.graphics.getDeltaTime()) {
            stateTime = 0;
        } else if (!isRunning && stateTime == 6 * Gdx.graphics.getDeltaTime()) {
            stateTime = 0;
        }
        if (Gdx.input.justTouched() && !isGameOver) {
            isRunning = false;
            currentAnimation = jumpAnimation;
            stateTime = 0f;
        }
        if (!isRunning && currentAnimation.isAnimationFinished(stateTime) && !isGameOver) {
            isRunning = true;
            currentAnimation = runAnimation;
            stateTime = 0f;
        }
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (isGameOver) {
            font.draw(batch , "Game Over ! Your Score = "+score  ,700 , 500) ;
            batch.end() ;
            return ;
        }
        font.draw(batch,"Your Score : "+score , 100 , 700) ;
        obstacleService.updateObstacles(140 , score);
        obstacleService.drawObstacles(batch) ;
        batch.draw(Textures.SUN.getTexture(), 700 , 600 , 100 , 100) ;
        camera.update();
        float yPosition = 100f; // Default running Y position
        batch.draw(Textures.CURVE.getTexture(), 0 ,100 , Gdx.graphics.getWidth() , 5);
        if (!isRunning) {
            float jumpDuration = jumpAnimation.getAnimationDuration();
            float progress = Math.min(stateTime / jumpDuration, 1f);
            yPosition = 200 + (float) Math.sin(progress * Math.PI) * 100; // Smooth arc
        }
        if (obstacleService.hasCollision(140 , (int)yPosition)) {
            font.setColor(Color.RED);
            isGameOver = true ;
        }
        batch.draw(frame, 140f, yPosition, 64, 64);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
