package com.kaan.savehostiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kaan.savehostiles.model.Textures;
import com.kaan.savehostiles.service.BulletService;
import com.kaan.savehostiles.service.PersonService;
import com.kaan.savehostiles.service.SpeedTreasureService;
import com.kaan.savehostiles.service.TreasureService;
import com.kaan.savehostiles.service.WeaponService;
import com.kaan.savehostiles.service.WeaponTreasureService;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SaveHostiles extends ApplicationAdapter {

    private static Long score ;
    private SpriteBatch batch;

    private OrthographicCamera camera ;

    private static SpriteBatch staticBatch ;

    private BulletService bulletService ;

    private PersonService personService ;

    private TreasureService weaponTreasureService ;

    private TreasureService speedTreasureService ;

    private WeaponService weaponService ;

    private BitmapFont font ;

    private Long lastCreatedPersonInMs ;

    private Long lastCreatedWeaponTreasureInMs ;

    private Long lastCreadtedSpeedTreasureInMs ;

    private Stage stage ;

    private Skin skin ;

    private TextButton fireButton ;

    static {
        score = 0L ;
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        staticBatch = batch ;
        Textures.AK_47.setTexture(new Texture(Gdx.files.internal("ak-47.jpg")));
        Textures.AK_47_SHOOTING.setTexture(new Texture(Gdx.files.internal("ak47-shoot.png")));
        Textures.M4A4.setTexture(new Texture(Gdx.files.internal("m4a4.jpg")));
        Textures.M4A4_SHOOTING.setTexture(new Texture(Gdx.files.internal("m4a4_shoot.png")));
        Textures.DEAGLE.setTexture(new Texture(Gdx.files.internal("deagle.jpg")));
        Textures.DEAGLE_SHOOTING.setTexture(new Texture(Gdx.files.internal("deagle_shoot.png")));
        Textures.GLOCK.setTexture(new Texture(Gdx.files.internal("glock.jpg")));
        Textures.GLOCK_SHOOTING.setTexture(new Texture(Gdx.files.internal("glock_shoot.png")));
        Textures.HOSTILE.setTexture(new Texture(Gdx.files.internal("rehine.jpg")));
        Textures.HOSTILE_AFTER_SHOT.setTexture(new Texture(Gdx.files.internal("dead_hostile.jpg")));
        Textures.TERRORIST.setTexture(new Texture(Gdx.files.internal("terorist.jpg")));
        Textures.TERRORIST_AFTER_SHOT.setTexture(new Texture(Gdx.files.internal("terorist_dead.jpg")));
        Textures.SPEED_1_5.setTexture(new Texture(Gdx.files.internal("1.5.jpg")));
        Textures.SPEED_2.setTexture(new Texture(Gdx.files.internal("x-2.png")));
        Textures.SPEED_3.setTexture(new Texture(Gdx.files.internal("x-3.png")));
        Textures.BULLET.setTexture(new Texture(Gdx.files.internal("bullet.jpg")));
        camera = new OrthographicCamera() ;
        camera.setToOrtho(false,1200 , 800);
        camera.update();
        font = new BitmapFont() ;
        font.setColor(Color.RED);
        lastCreatedPersonInMs = System.currentTimeMillis() ;
        lastCreatedWeaponTreasureInMs = System.currentTimeMillis() ;
        lastCreadtedSpeedTreasureInMs = System.currentTimeMillis() ;
        stage = new Stage(new ScreenViewport(camera)) ;
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json")) ;

        fireButton = new TextButton("Fire" , skin) ;
        fireButton.setSize(100, 50);
        fireButton.setPosition(100, Gdx.graphics.getHeight() / 2f);

        fireButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bulletService.createBullet();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                weaponService.getMainWeapon().setShooting(false);
            }
        });

        stage.addActor(fireButton);
        bulletService = BulletService.getInstance() ;
        personService = PersonService.getInstance() ;
        weaponTreasureService = WeaponTreasureService.getInstance() ;
        speedTreasureService = SpeedTreasureService.getInstance() ;
        weaponService = WeaponService.getInstance() ;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act();
        stage.draw();
        stage.getViewport().setCamera(camera);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        weaponService.swapGunTexture();
        if (Gdx.input.isTouched()) {
            // Önce ekran koordinatlarını al
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Kamera koordinatlarına çevir
            camera.unproject(touch);

            // Stage koordinatlarına çevir
            Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            // Butonun bounds'ını al
            float buttonX = fireButton.getX();
            float buttonY = fireButton.getY();
            float buttonWidth = fireButton.getWidth();
            float buttonHeight = fireButton.getHeight();

            // Dokunma noktası butonun üzerinde mi kontrol et
            boolean touchingButton = touch.x >= buttonX && touch.x <= buttonX + buttonWidth &&
                touch.y >= buttonY && touch.y <= buttonY + buttonHeight;

            if (!touchingButton) {
                weaponService.updateGunPosition((int) touch.y);
            }
        }
        bulletService.bulletPersonControl();
        bulletService.bulletTreasureControl();
        bulletService.drawBullets();
        bulletService.updateBullets();
        if (System.currentTimeMillis() - lastCreatedPersonInMs >= 2000) {
            personService.createRandomPerson();
            lastCreatedPersonInMs = System.currentTimeMillis() ;
        }
        personService.drawPersons();
        if (System.currentTimeMillis() - lastCreatedWeaponTreasureInMs >= 12000) {
            weaponTreasureService.createRandomTreasure();
            lastCreatedWeaponTreasureInMs = System.currentTimeMillis() ;
        }
        if (System.currentTimeMillis() - lastCreadtedSpeedTreasureInMs >= 12000) {
            speedTreasureService.createRandomTreasure();
            lastCreadtedSpeedTreasureInMs = System.currentTimeMillis() ;
        }
        weaponTreasureService.drawTreasures();
        speedTreasureService.drawTreasures();
        font.draw(batch , score.toString() , 100 , 100) ;
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    public static Long getScore() {
        return score;
    }

    public static void setScore(Long score) {
        SaveHostiles.score = score;
    }


    public static SpriteBatch getBatch (){
        return staticBatch ;
    }



}
