package com.example.casa.TheMorkians;

import android.graphics.Typeface;
import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends SimpleBaseGameActivity
        implements IOnSceneTouchListener {
    private int width = 1280, height = 700;
    private Camera camera;
    private BitmapTextureAtlas bitmapTextureAtlas;
    private TextureRegion playerRegion, ballRegion, balaPlayerRegion, backgroundregion;
    private Scene scene;
    private ArrayList<Sprite> ballList, bulletList;
    private Text lifeText;
    private Font mFont;
    private int vidas;
    private Music backgroundMusic;
    private Sound shootSound;
    private ParallaxBackground parallaxBackground;
    private Player player;


    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        bitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(),2048, 2048);
        playerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                bitmapTextureAtlas, this, "nave.png", 0, 0);
        ballRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                bitmapTextureAtlas, this, "kamikaze.png", 200, 200);
        balaPlayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                bitmapTextureAtlas, this, "balaplayer.png", 300,300);
        backgroundregion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                bitmapTextureAtlas,this,"background.png",400,400);

        bitmapTextureAtlas.load();

        this.mFont = FontFactory.create(getFontManager(), getTextureManager(),
                256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        this.mFont.load();
        MusicFactory.setAssetBasePath("mfx/");
        backgroundMusic = MusicFactory.createMusicFromAsset(getMusicManager(),this, "wagner_the_ride_of_the_valkyries.ogg");
        SoundFactory.setAssetBasePath("mfx/");
        shootSound=SoundFactory.createSoundFromAsset(getSoundManager(),this,"lightsaber_01.wav");
    }

    @Override
    protected Scene onCreateScene() {
        ballList = new ArrayList<>();
        bulletList = new ArrayList<>();
        vidas = 3;


        scene = new Scene();
        scene.setBackground(new Background(Color.WHITE));

        player = new Player(playerRegion.getWidth()/2,
                height/2, playerRegion,
                getVertexBufferObjectManager());

        scene.attachChild(player);

        addEnemyHandler();
        removeEnemyHandler();
        collisionHandler();
        collisionMonsterEnemyHandler();
        createBackground();

        scene.setOnSceneTouchListener(this);

        lifeText = new Text(80, height-50, mFont, "Lifes: 3", getVertexBufferObjectManager());
        scene.attachChild(lifeText);

        backgroundMusic.setLooping(true);

        backgroundMusic.play();

        return scene;
    }

    private void createBackground()
    {
        parallaxBackground= new ParallaxBackground(0, 0, 0);
        parallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0, new Sprite(
                backgroundregion.getWidth()/2, backgroundregion.getHeight()/2, backgroundregion,getVertexBufferObjectManager())));
        scene.setBackground(parallaxBackground);
    }


    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, width, height);
        EngineOptions options= new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new FillResolutionPolicy(), camera);

        options.getAudioOptions().setNeedsMusic(true);
        options.getAudioOptions().setNeedsSound(true);

        return options;
    }


    private void addEnemy()
    {
        Random r = new Random();
        int x = (int)(width + ballRegion.getWidth());
        int y = r.nextInt((int)(height-ballRegion.getHeight()));

        Sprite bulletSprite = new Sprite(x, y, ballRegion, getVertexBufferObjectManager());

        int duration = r.nextInt(4) + 2;
        MoveXModifier moveXModifier = new MoveXModifier(duration,
                bulletSprite.getX(), -bulletSprite.getWidth());
        bulletSprite.registerEntityModifier(moveXModifier);

        ballList.add(bulletSprite);
        scene.attachChild(bulletSprite);
    }

    private void addEnemyHandler()
    {
        TimerHandler timerHandler = new TimerHandler(3, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        addEnemy();
                    }
                });
        scene.registerUpdateHandler(timerHandler);
    }

    private void removeEnemyHandler()
    {
        IUpdateHandler updateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                ArrayList<Sprite> toRemove = new ArrayList<>();

                for(Sprite sprite : ballList)
                {
                    if (sprite.getX() <= - sprite.getWidth())
                    {
                        scene.detachChild(sprite);
                        toRemove.add(sprite);
                    }
                }
                ballList.removeAll(toRemove);
                Log.i("mygame","total: " + ballList.size());
            }

            @Override
            public void reset() {

            }
        };
        scene.registerUpdateHandler(updateHandler);
    }

    private void shootBullet(float pX, float pY)
    {
        Sprite bullet = new Sprite(player.getX(),
                player.getY(), balaPlayerRegion, getVertexBufferObjectManager());
        scene.attachChild(bullet);
        bulletList.add(bullet);

        MoveModifier modifier = new MoveModifier(3,
                bullet.getX(), bullet.getY(),
                width, player.getY());
        bullet.registerEntityModifier(modifier);
        shootSound.play();
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown())
        {
            shootBullet(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
            return true;
        }
        return false;
    }

    private void collisionHandler()
    {
        IUpdateHandler handler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                ArrayList<Sprite> ballsToRemove = new ArrayList<>();
                ArrayList<Sprite> bombsToRemove = new ArrayList<>();

                for(Sprite ball : ballList)
                {
                    if (ball.getX() <= -ball.getWidth()) break;

                    for(Sprite bullet : bulletList)
                    {
                        if (bullet.getX() >= width ||
                                bullet.getY() >= height + bullet.getHeight() ||
                                bullet.getY() <= -bullet.getHeight())
                        {
                            scene.detachChild(bullet);
                            bombsToRemove.add(bullet);
                            continue;
                        }

                        if (ball.collidesWith(bullet))
                        {
                            scene.detachChild(bullet);
                            scene.detachChild(ball);

                            ballsToRemove.add(ball);
                            bombsToRemove.add(bullet);
                        }
                    }
                }
                bulletList.removeAll(bombsToRemove);
                ballList.removeAll(ballsToRemove);
            }

            @Override
            public void reset() {

            }
        };
        scene.registerUpdateHandler(handler);
    }

    private void collisionMonsterEnemyHandler()
    {
        IUpdateHandler handler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                ArrayList<Sprite> ballsToRemove = new ArrayList<>();

                for(Sprite ball : ballList)
                {
                    if (ball.collidesWith(player))
                    {
                        scene.detachChild(ball);
                        vidas--;
                        lifeText.setText("Lifes: " + vidas);
                        ballsToRemove.add(ball);
                    }
                }
                ballList.removeAll(ballsToRemove);
            }

            @Override
            public void reset() {

            }
        };
        scene.registerUpdateHandler(handler);
    }
}


