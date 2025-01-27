package com.kaan.dropcatchinggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class DropCatching extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture bucket;
    private Rectangle rectBucket;
    private Array<Rectangle> drops;
    private DropService dropService;
    private Texture waterDrop;
    private Long lastDroppingTime;
    private Long score;

    // Damla düşüş hızı
    private float dropSpeed = 1;

    // Yazı tipi
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        bucket = new Texture(Gdx.files.internal("buckett.png"));
        rectBucket = new Rectangle();
        rectBucket.width = 64;
        rectBucket.height = 64;
        rectBucket.x = 800 / 2 - bucket.getWidth() / 2;
        rectBucket.y = 20;
        drops = new Array<>();
        dropService = new DropService();
        waterDrop = new Texture(Gdx.files.internal("drop.png"));
        lastDroppingTime = TimeUtils.nanoTime();
        score = 0L;

        // BitmapFont oluştur
        font = new BitmapFont();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClearColor(0F, 0.2F, 0F, 255F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Kovayı çiz
        batch.draw(bucket, rectBucket.x, rectBucket.y);

        // Damlaları çiz
        for (Rectangle drop : drops) {
            batch.draw(waterDrop, drop.x, drop.y);
        }

        // Skoru çiz
        font.draw(batch, "Score: " + score, 10, 470); // Sol üst köşeye yaz

        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchingPosition = new Vector3();
            touchingPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0F);
            camera.unproject(touchingPosition);
            rectBucket.x = touchingPosition.x - rectBucket.width / 2;
        }

        if (TimeUtils.nanoTime() - lastDroppingTime > 1000000000L) {
            makeDrops();
        }

        Iterator<Rectangle> dropIterator = drops.iterator();
        while (dropIterator.hasNext()) {
            Rectangle drop = dropIterator.next();
            drop.setY(drop.getY() - dropSpeed);
            if (drop.y + 64 < 0) {
                dropIterator.remove();
            }
        }

        dropIterator = drops.iterator();
        while (dropIterator.hasNext()) {
            Rectangle drop = dropIterator.next();
            if (Math.abs(drop.x - rectBucket.x) < 64 && drop.y - rectBucket.y < 64 && drop.y > rectBucket.y) {
                dropIterator.remove();
                score += 20L;
            }
        }
    }

    private void makeDrops() {
        Rectangle rectDrop = new Rectangle();
        rectDrop.x = dropService.getRandomXValueOfDrop();
        rectDrop.y = 480;
        rectDrop.width = 64;
        rectDrop.height = 64;
        drops.add(rectDrop);
        lastDroppingTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bucket.dispose();
        waterDrop.dispose();
        font.dispose(); // BitmapFont'u temizle
    }
}
