package com.example.casa.TheMorkians;

<<<<<<< HEAD
=======
import android.widget.Toast;

>>>>>>> ce2caec5accbb25cd5be65f56c67d986a8129bea
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

import java.lang.reflect.Array;
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
    private Enemy kamikazeEnemy;
    private Enemy bomberEnemy;
    private Enemy heavyBomberEnemy;
    private ObjectsScene planet,moon;
    private ArrayList<Bala> listaBalasEnemy;
    private ArrayList<Bala> listaBalasPlayer;

    private void addToScore(int i)
    {
        score += i;
        scoreText.setText("Score: " + score);
    }

    @Override
    public void createScene() {

        enemyList=new ArrayList<>();
        BalaManager.Initialize();
        createBackground();
        createHUD();
        createControls();
        addEnemyHandler();
        addBomberEnemyHandler();
        addHeavyBomberEnemyHandler();
        addObjectsInTheScene();
        createLevel();


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
<<<<<<< HEAD
        resourcesManager.levelMusic.play();


=======
        colisions();
>>>>>>> ce2caec5accbb25cd5be65f56c67d986a8129bea
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

        kamikazeEnemy = new Enemy(x, y, resourcesManager.gameKamikazeRegion, vbom, resourcesManager);
        kamikazeEnemy.setScale(0.8f);


        PhysicsHandler enemyPhysicsHandler = new PhysicsHandler(kamikazeEnemy);
        kamikazeEnemy.registerUpdateHandler(enemyPhysicsHandler);

        int duration = enemyRandom.nextInt(4) + 2;
        int velocity=10;

        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                kamikazeEnemy.getX(), -kamikazeEnemy.getWidth());
        kamikazeEnemy.registerEntityModifier(moveXModifier);
        MoveYModifier moveYModifier=new MoveYModifier(duration*velocity,
                kamikazeEnemy.getY(),(float)Math.cos(enemyRandom.nextFloat()*50)*kamikazeEnemy.getX());
        kamikazeEnemy.registerEntityModifier(moveYModifier);

        attachChild(kamikazeEnemy);
        enemyList.add(kamikazeEnemy);
<<<<<<< HEAD





=======
>>>>>>> ce2caec5accbb25cd5be65f56c67d986a8129bea
        addShoot();
    }
    private void addShoot()
    {
        TimerHandler timerHandler = new TimerHandler(3, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {

                        for (Enemy enemy :enemyList
                                )
                        {
                            Bala bala = BalaManager.shootBalaInimigo(enemy.getX(), enemy.getY());
                            if(!bala.hasParent()){
                                attachChild(bala);
                            }
                        }

                        //Remover balas que saem do ecrã
                        BalaManager.RemoveBalas();

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
        int velocity=12;

        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                bomberEnemy.getX(), -bomberEnemy.getWidth());
        bomberEnemy.registerEntityModifier(moveXModifier);

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
        int velocity=17;

        MoveXModifier moveXModifier = new MoveXModifier(duration*velocity,
                heavyBomberEnemy.getX(), -heavyBomberEnemy.getWidth());
        heavyBomberEnemy.registerEntityModifier(moveXModifier);
        //heavyBomberEnemy.shoot(heavyBomberEnemy.getX(),heavyBomberEnemy.getY());

        attachChild(heavyBomberEnemy);


    }
    private void addObjectsInTheScene()
    {
        int x=(int)(camera.getCenterX());
        int y=(int)(camera.getCenterX());

        moon=new ObjectsScene(x*8,y-10,resourcesManager.objectMoonRegion,vbom);
        moon.setScale(0.6f);

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
                if(player.getY()+player.getHeight()>=camera.getHeight())
                {

                    playerPhysicsHandler.setVelocity(pValueX * 200,-20);
                }
                else if(player.getY()<=player.getHeight())
                {
                    playerPhysicsHandler.setVelocity(pValueX * 200,+20);

                }
                else if (player.getX()<=camera.getCenterX()-camera.getWidth()/2+player.getWidth()/2)
                {
                    playerPhysicsHandler.setVelocity(+20,pValueY*200);
                }
                else if (player.getX()+player.getWidth()/2>=camera.getCenterX()+camera.getWidth()/2)
                {
                    playerPhysicsHandler.setVelocity(-20,pValueY*200);
                }
                else
                    playerPhysicsHandler.setVelocity(pValueX * 200, pValueY * 200);

            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {

            }
        }

        );


        setChildScene(velocityOnScreenControl);

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
                //ArrayList<Sprite> balasAremover = new ArrayList<Sprite>();
                //ArrayList<Sprite> inimigosAremover = new ArrayList<Sprite>();
                //percorre lista de balas dos inimigos e verifica colisao com player
                for(Bala bala : listaBalasEnemy)
                {
                    if(bala.collidesWith(player))
                    {
                        //player.vidas--;
                        //player perde vida
                        //bala destruida
                        //Toast.makeText(resourcesManager.activity, "colisao bala com player", Toast.LENGTH_SHORT).show();
                    }
                }
                //percorre lista de balas do player e verifica colisao com enimigos
                for(Bala bala:listaBalasPlayer)
                {
                    for(Enemy enemy:enemyList)
                    {
                        if(bala.collidesWith(enemy))
                        {
                            //inimigo explode violentamente
                            //bala destruida
                        }
                    }

                }

                for(Sprite enemy:enemyList)
                {
                    if(enemy.collidesWith(player))
                    {
                        //destruir player
                        //destruir enemy
                    }
                }

            }

            @Override
            public void reset()
            {

            }
        };
        registerUpdateHandler(colisionHandler);

    }
}
