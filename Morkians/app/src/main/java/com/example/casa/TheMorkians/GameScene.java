package com.example.casa.TheMorkians;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by PedroMarques on 30-12-2015.
 */
public class GameScene extends BaseScene{

    private HUD gameHUD;
    private Text scoreText;
    private int score = 0;
    private Player player;
    private PhysicsHandler playerPhysicsHandler;

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
        attachChild(player);
    }

    private void createControls(){
        AnalogOnScreenControl velocityOnScreenControl = new AnalogOnScreenControl(84, 84, camera,
                resourcesManager.gameVirtualJoystickBaseRegion, resourcesManager.gameVirtualJoystickPadRegion, 0.1f,
                vbom, new IAnalogOnScreenControlListener() {
            @Override
            public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                playerPhysicsHandler.setVelocity(pValueX * 200, pValueY * 200);
            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
               /* Não fazemos nada no clique neste controlo */
            }
        });

        setChildScene(velocityOnScreenControl);

    }
}
