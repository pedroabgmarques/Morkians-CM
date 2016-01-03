package com.example.casa.TheMorkians;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by PedroMarques on 30-12-2015.
 */
public class GameScene extends BaseScene{

    private HUD gameHUD;
    private Text scoreText;
    private int score = 0;
    private Player player;
    private PhysicsHandler playerPhysicsHandler;
    private ArrayList<Enemy> enemyList;
    private Enemy enemy;

    private void addToScore(int i)
    {
        score += i;
        scoreText.setText("Score: " + score);
    }

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createLevel();
        createControls();
        addEnemyHandler();
    }

    @Override
    public void onBackKeyPressed()
    {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(400, 240);

        // TODO code responsible for disposing scene
        // removing all game scene objects.
    }

    private void createBackground()
    {
        attachChild(new Sprite(0, 240, resourcesManager.gameBackgroundRegion, vbom) {
            //Magia negra para as texturas ficarem mais bonitas
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createHUD()
    {
        gameHUD = new HUD();

        // CREATE SCORE TEXT
        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("Score: 0");
        gameHUD.attachChild(scoreText);

        camera.setHUD(gameHUD);
    }

    private void createLevel(){

        player = new Player(80, 230, resourcesManager.gamePlayerRegion, vbom);
        player.setScale(0.7f);
        playerPhysicsHandler = new PhysicsHandler(player);
        player.registerUpdateHandler(playerPhysicsHandler);
        //A linha abaixo faz a camara seguir o jogador
        //camera.setChaseEntity(player);

        //Mover a camara
        IUpdateHandler cameraUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                camera.setCenter(camera.getCenterX() + 1, camera.getCenterY());
                player.setPosition(player.getX() + 1, player.getY());
            }

            @Override
            public void reset() {
                camera.setCenter(0, 0);
            }
        };

        registerUpdateHandler(cameraUpdateHandler);
        attachChild(player);
    }

    private void createNewEnemy()
    {
        Random enemyRandom = new Random();
        int x = (int)(camera.getCenterX()+ camera.getWidth()/2 + resourcesManager.gameKamikazeRegion.getWidth());
        int y = enemyRandom.nextInt((int)(camera.getHeight() - resourcesManager.gameKamikazeRegion.getHeight()));

        enemy = new Enemy(x, y, resourcesManager.gameKamikazeRegion, vbom);
        enemy.setScale(0.8f);

        PhysicsHandler enemyPhysicsHandler = new PhysicsHandler(enemy);
        enemy.registerUpdateHandler(enemyPhysicsHandler);

        int duration = enemyRandom.nextInt(4) + 2;
        int velocity=10;
        double incremetalValue=90;
        incremetalValue++;
        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                enemy.getX(), -enemy.getWidth());
        enemy.registerEntityModifier(moveXModifier);
        MoveYModifier moveYModifier=new MoveYModifier(duration*velocity,enemy.getY(),(float)Math.cos(enemyRandom.nextFloat()*50)*enemy.getX());
        enemy.registerEntityModifier(moveYModifier);


        //enemyList.add(enemy);
        attachChild(enemy);
    }

    private void addEnemyHandler()
    {
        TimerHandler timerHandler = new TimerHandler(1, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        createNewEnemy();
                    }
                });
        registerUpdateHandler(timerHandler);

    }

    private void createControls(){
        AnalogOnScreenControl velocityOnScreenControl = new AnalogOnScreenControl(84, 84, camera,
                resourcesManager.gameVirtualJoystickBaseRegion, resourcesManager.gameVirtualJoystickPadRegion, 0.1f,
                vbom, new IAnalogOnScreenControlListener() {
            @Override
            public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                playerPhysicsHandler.setVelocity(pValueX * 200, pValueY * 200);
                //if(player.getY()+player.getHeight()>=camera.getHeight()||player.getY()<=player.getHeight())
                //{
                //    playerPhysicsHandler.setVelocity(pValueX * 200,0);

                //}
                //else if (player.getX()>=camera.getWidth()||player.getX()<=0)
                //{
                //    playerPhysicsHandler.setVelocity(0,pValueY*200);
                //}
                //else
                //    playerPhysicsHandler.setVelocity(pValueX * 200, pValueY * 200);

            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
               /* Não fazemos nada no clique neste controlo
               * Se calhar podia-se disparar aqui?
               * Sprite bomb = new Sprite(monstroSprite.getX(),
                monstroSprite.getY(), bombRegion, getVertexBufferObjectManager());
        scene.attachChild(bomb);
        bombList.add(bomb);

        float offX = pX - monstroSprite.getX();
        float offY = pY - monstroSprite.getY();
        float ratio = offY/offX;

        int finalX = (int)(width + bomb.getWidth());
        int finalY = (int)(ratio * finalX + monstroSprite.getY());

        MoveModifier modifier = new MoveModifier(3,
                bomb.getX(), bomb.getY(),
                finalX, finalY);
        bomb.registerEntityModifier(modifier);
               * */
            }
        });

        setChildScene(velocityOnScreenControl);

    }
}
