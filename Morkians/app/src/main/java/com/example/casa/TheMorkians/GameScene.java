package com.example.casa.TheMorkians;



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

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by PedroMarques on 30-12-2015.
 */
public class GameScene extends BaseScene implements IOnSceneTouchListener {

    private HUD gameHUD;
    private Text scoreText;
    private Player player;
    private PhysicsHandler playerPhysicsHandler;
    private ArrayList<Enemy> enemyList;
    private Enemy kamikazeEnemy;
    private Enemy bomberEnemy;
    private Enemy heavyBomberEnemy;
    private ObjectsScene planet,moon;
    private ArrayList<Bala> listaBalasEnemy;
    private ArrayList<Bala> listaBalasPlayer;
    private ArrayList<Enemy> InimigosRemover;
    private int backgroundInicio=8018;
    private int contadorFundos=1;
    private Sprite fundo, primeiroFundo, fundoAnterior;

    private void addToScore(int i)
    {
        resourcesManager.score += i;
        scoreText.setText("Score: " + resourcesManager.score);
    }

    @Override
    public void createScene() {

        enemyList=new ArrayList<Enemy>();
        InimigosRemover = new ArrayList<Enemy>();
        resourcesManager.score = 0;
        BalaManager.Initialize();
        createBackground();
        createHUD();
        createControls();
        addEnemyHandler();
        addBomberEnemyHandler();
        addHeavyBomberEnemyHandler();
        addObjectsInTheScene();
        createLevel();
        resourcesManager.levelMusic.play();
        resourcesManager.mainMenuMusic.stop();



    }

    @Override
    public void onBackKeyPressed()
    {

        SceneManager.getInstance().loadMenuScene(engine);
        resourcesManager.levelMusic.stop();

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
        primeiroFundo = new Sprite(0, 240, resourcesManager.gameBackgroundRegion, vbom);
        attachChild(primeiroFundo);

        //attachChild(new Sprite(backgroundInicio, 240, resourcesManager.gameBackgroundRegion, vbom));
    }

    private void changeBackground()
    {
        if(camera.getCenterX() >= 3609*contadorFundos)
        {
            //fundoAnterior = fundo;
            fundo = new Sprite(backgroundInicio * contadorFundos , 240,resourcesManager.gameBackgroundRegion,vbom);

            float playerX = player.getX();
            float playerY = player.getY();
            detachChild(player);
            attachChild(fundo);
            attachChild(player);
            player.setX(playerX);
            player.setY(playerY);
            contadorFundos++;

            //detachChild(fundoAnterior);
        }
        //if(camera.getCenterX() >= 3620 * contadorFundos)
        //{
        //    if(primeiroFundo!=null)
        //    {
        //        detachChild(primeiroFundo);
        //    }
        //    detachChild(fundoAnterior);
        //}
    }

    private void createHUD()
    {
        gameHUD = new HUD();

        // CREATE SCORE TEXT
        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("Score: " + resourcesManager.score);
        gameHUD.attachChild(scoreText);

        camera.setHUD(gameHUD);
    }

    private void createLevel(){

        player = new Player(80, 230, resourcesManager.gamePlayerRegion, vbom);
        player.setScale(0.7f);

        playerPhysicsHandler = new PhysicsHandler(player);
        player.registerUpdateHandler(playerPhysicsHandler);
        setOnSceneTouchListener(this);
        colisions();

        //A linha abaixo faz a camara seguir o jogador
        //camera.setChaseEntity(player);

        //Mover a camara

        IUpdateHandler cameraUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                camera.setCenter(camera.getCenterX() + 1, camera.getCenterY());
                if(player!=null)
                {
                    player.setPosition(player.getX() + 1, player.getY());
                }

                changeBackground();
            }

            @Override
            public void reset() {
                camera.setCenter(0, 0);
            }
        };

        registerUpdateHandler(cameraUpdateHandler);


        attachChild(player);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown())
        {
            if(player!=null)
            {
                Bala novaBala = BalaManager.shootBalaPlayer(player.getX() + player.getWidth() / 2, player.getY() - player.getHeight() / 3);
                attachChild(novaBala);
                return true;
            }
        }
        return false;
    }

    private void createNewEnemy()
    {
        Random enemyRandom = new Random();
        int x = (int)(camera.getCenterX()+ camera.getWidth()/2 + resourcesManager.gameKamikazeRegion.getWidth());
        int y = enemyRandom.nextInt((int) camera.getHeight())+1;

        kamikazeEnemy = new Enemy(x, y, resourcesManager.gameKamikazeRegion, vbom, resourcesManager);
        kamikazeEnemy.setScale(0.8f);


        PhysicsHandler enemyPhysicsHandler = new PhysicsHandler(kamikazeEnemy);
        kamikazeEnemy.registerUpdateHandler(enemyPhysicsHandler);

        int duration = enemyRandom.nextInt(4) + 2;

        int velocity=3;

        if (player!=null)
        {
            MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                    kamikazeEnemy.getX(), player.getX()-25);

            kamikazeEnemy.registerEntityModifier(moveXModifier);
            MoveYModifier moveYModifier=new MoveYModifier(duration*velocity,
                    kamikazeEnemy.getY(),player.getY());
            kamikazeEnemy.registerEntityModifier(moveYModifier);

        }
        else
        {
            MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                    kamikazeEnemy.getX(), -kamikazeEnemy.getX()-1000);

            kamikazeEnemy.registerEntityModifier(moveXModifier);
            MoveYModifier moveYModifier=new MoveYModifier(duration*velocity,
                    kamikazeEnemy.getY(),-kamikazeEnemy.getX()-1000);
            kamikazeEnemy.registerEntityModifier(moveYModifier);
        }



        attachChild(kamikazeEnemy);
        enemyList.add(kamikazeEnemy);

        addShoot();
    }
    private void addShoot()
    {
        Random random=new Random();
        int shootTime=random.nextInt(6)+2;
        TimerHandler timerHandler = new TimerHandler(shootTime, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {

                        for (Enemy enemy :enemyList)
                        {

                            Bala bala = BalaManager.shootBalaInimigo(enemy.getX(), enemy.getY());
                            attachChild(bala);
                        }

                    }
                });

        registerUpdateHandler(timerHandler);

    }
    private void createNewBomberEnemy()
    {

        Random enemyRandom = new Random();
        int x = (int)(camera.getCenterX()+ camera.getWidth()/2 + resourcesManager.gameBomberRegion.getWidth());
        int y = enemyRandom.nextInt((int)(camera.getHeight() - resourcesManager.gameBomberRegion.getHeight()));

        bomberEnemy = new Enemy(x, y, resourcesManager.gameBomberRegion, vbom, resourcesManager);
        bomberEnemy.setScale(0.5f);
        PhysicsHandler enemyPhysicsHandler = new PhysicsHandler(bomberEnemy);
        bomberEnemy.registerUpdateHandler(enemyPhysicsHandler);

        int duration = enemyRandom.nextInt(4) + 2;
        int velocity = 10;

        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                bomberEnemy.getX(), bomberEnemy.getX() - 1000);
        bomberEnemy.registerEntityModifier(moveXModifier);

        enemyList.add(bomberEnemy);

        attachChild(bomberEnemy);


    }

    private void createNewHeavyBomberEnemy()
    {
        Random enemyRandom= new Random();
        int x = (int)(camera.getCenterX()+ camera.getWidth()/2 + resourcesManager.gameHeavyBomberRegion.getWidth());
        int y = enemyRandom.nextInt((int)(camera.getHeight() - resourcesManager.gameHeavyBomberRegion.getHeight()));

        heavyBomberEnemy=new Enemy(x,y,resourcesManager.gameHeavyBomberRegion,vbom, resourcesManager);
        heavyBomberEnemy.setScale(0.5f);
        PhysicsHandler enemyPhysicsHandler = new PhysicsHandler(heavyBomberEnemy);
        heavyBomberEnemy.registerUpdateHandler(enemyPhysicsHandler);

        int duration = enemyRandom.nextInt(6) + 2;
        int velocity = 15;

        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                heavyBomberEnemy.getX(), heavyBomberEnemy.getX() - 1000);
        heavyBomberEnemy.registerEntityModifier(moveXModifier);
        //heavyBomberEnemy.shoot(heavyBomberEnemy.getX(),heavyBomberEnemy.getY());

        enemyList.add(heavyBomberEnemy);

        attachChild(heavyBomberEnemy);


    }
    private void addObjectsInTheScene()
    {
        int x=(int)(camera.getCenterX());
        int y=(int)(camera.getCenterX());

        moon=new ObjectsScene(x*8,y-10,resourcesManager.objectMoonRegion,vbom);
        moon.setScale(0.3f);

        MoveXModifier moveMoonXModifier = new MoveXModifier(75,
                moon.getX(), -moon.getWidth());
        moon.registerEntityModifier(moveMoonXModifier);
        attachChild(moon);

        planet= new ObjectsScene(x*7,y,resourcesManager.objectPlanetRegion,vbom);
        planet.setScale(0.6f);

        MoveXModifier movePlanetXModifier = new MoveXModifier(90,
                planet.getX(), -planet.getWidth());
        planet.registerEntityModifier(movePlanetXModifier);

        attachChild(planet);



    }


    private void addEnemyHandler()
    {
        TimerHandler timerHandler = new TimerHandler(3, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        createNewEnemy();
                    }
                });
        registerUpdateHandler(timerHandler);

    }
    private void addBomberEnemyHandler()
    {
        TimerHandler timerHandler = new TimerHandler(4, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        createNewBomberEnemy();
                    }
                });
        registerUpdateHandler(timerHandler);

    }
    private void addHeavyBomberEnemyHandler()
    {
        TimerHandler timerHandler = new TimerHandler(6, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        createNewHeavyBomberEnemy();
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
                //condiçoes que impedem oplayer de passar os limites da camara
                if(player!=null) {
                    if (player.getY() + player.getHeight() >= camera.getHeight()) {

                        playerPhysicsHandler.setVelocity(pValueX * 200, -20);
                    } else if (player.getY() <= player.getHeight()) {
                        playerPhysicsHandler.setVelocity(pValueX * 200, +20);

                    } else if (player.getX() <= camera.getCenterX() - camera.getWidth() / 2 + player.getWidth() / 2) {
                        playerPhysicsHandler.setVelocity(+20, pValueY * 200);
                    } else if (player.getX() + player.getWidth() / 2 >= camera.getCenterX() + camera.getWidth() / 2) {
                        playerPhysicsHandler.setVelocity(-20, pValueY * 200);
                    } else
                        playerPhysicsHandler.setVelocity(pValueX * 200, pValueY * 200);
                }
            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {

            }
        }

        );

        setChildScene(velocityOnScreenControl);
    }

    private void ExplodeStuff(float x, float y){
        AnimatedSprite explosion = new AnimatedSprite(x, y, resourcesManager.gameExplosionRegion,
                vbom);
        attachChild(explosion);
        explosion.animate(10, 0);
    }

    private void colisions()
    {
        //enemyList
        //listaBalasEnemy
        //listaBalasPlayer

        IUpdateHandler colisionHandler = new IUpdateHandler()
        {
            @Override
            public void onUpdate(float pSecondsElapsed)
            {
                listaBalasEnemy = BalaManager.getBalasInimigo();
                listaBalasPlayer = BalaManager.getBalasPlayer();
                ArrayList<Bala> balasAremoverEnemy = new ArrayList<Bala>();
                ArrayList<Bala> balasAremoverPlayer = new ArrayList<Bala>();
                ArrayList<Enemy> inimigosAremover = new ArrayList<Enemy>();
                //percorre lista de balas dos inimigos e verifica colisao com player
                for(Bala bala : listaBalasEnemy)
                {
                    if(player != null && bala.collidesWith(player))
                    {
                        float posPlayX,posPlayY;
                        posPlayX=player.getX();
                        posPlayY=player.getY();
                        ExplodeStuff(posPlayX, posPlayY);

                        detachChild(player);
                        player=null;
                        detachChild(bala);
                        balasAremoverEnemy.add(bala);


                        TimerHandler timerHandler=new TimerHandler(2, false, new ITimerCallback() {
                            @Override
                            public void onTimePassed(TimerHandler pTimerHandler) throws IOException
                            {
                                SceneManager.getInstance().createFinishScene();
                            }
                        });

                        registerUpdateHandler(timerHandler);



                    }
                }

                //percorre lista de balas do player e verifica colisao com enimigos
                for(Bala bala:listaBalasPlayer)
                {
                    for(Enemy enemy:enemyList)
                    {
                        if(bala.collidesWith(enemy))
                        {
                            balasAremoverPlayer.add(bala);
                            detachChild(bala);
                            inimigosAremover.add(enemy);
                            detachChild(enemy);
                            addToScore(6);

                            ExplodeStuff(bala.getX(), bala.getY());

                        }
                    }

                }

                //percorre lista de inimigos e verifica se colide com o player
                for(Enemy enemy:enemyList)
                {
                    if(player != null && enemy.collidesWith(player))
                    {
                        float posPlayX,posPlayY,posEnemX,posEnemY;
                        posPlayX=player.getX();
                        posPlayY=player.getY();
                        posEnemX=enemy.getX();
                        posEnemY=enemy.getY();

                        detachChild(player);
                        player=null;
                        inimigosAremover.add(enemy);
                        detachChild(enemy);


                        ExplodeStuff(posPlayX, posPlayY);
                        ExplodeStuff(posEnemX, posEnemY);
                        TimerHandler timerHandler=new TimerHandler(2,false, new ITimerCallback() {
                            @Override
                            public void onTimePassed(TimerHandler pTimerHandler) throws IOException
                            {
                                SceneManager.getInstance().createFinishScene();
                            }
                        });
                        registerUpdateHandler(timerHandler);



                        //onBackKeyPressed();

                    }
                }
                listaBalasPlayer.removeAll(balasAremoverPlayer);
                listaBalasEnemy.removeAll(balasAremoverEnemy);
                enemyList.removeAll(inimigosAremover);

                //Mover balas
                for(Bala bala: BalaManager.getBalasInimigo()){
                    bala.setPosition(bala.getX() - 3, bala.getY());
                }
                for(Bala bala: BalaManager.getBalasPlayer()){
                    bala.setPosition(bala.getX() + 6, bala.getY());
                }

                //Remover balas que saem do ecrã
                BalaManager.RemoveBalas();
                for(Bala bala: BalaManager.listaBalasDetach){
                    detachChild(bala);
                }
                BalaManager.listaBalasDetach.clear();

                //Remover inimigos que saem do ecrã
                for(Enemy inimigo: enemyList){
                    if(inimigo.getX() < camera.getCenterX() - camera.getWidth() - inimigo.getWidth() / 2){
                        InimigosRemover.add(inimigo);
                    }
                }
                for(Enemy inimigo: InimigosRemover){
                    enemyList.remove(inimigo);
                    detachChild(inimigo);
                }
                InimigosRemover.clear();
            }

            @Override
            public void reset()
            {

            }
        };
        registerUpdateHandler(colisionHandler);

    }



}
