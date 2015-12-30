package com.example.casa.TheMorkians;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by Victor on 30/12/2015.
 */
public class SplashScene extends BaseScene {

    private Sprite loadImage;
    @Override
    public void createScene()
    {
        loadImage = new Sprite(0, 0, resourcesManager.loadMenu_Region, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        loadImage.setScale(1f);
        loadImage.setPosition(400,240);
        attachChild(loadImage);

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene()
    {
        loadImage.detachSelf();
        loadImage.dispose();
        this.detachSelf();
        this.dispose();

    }
}
