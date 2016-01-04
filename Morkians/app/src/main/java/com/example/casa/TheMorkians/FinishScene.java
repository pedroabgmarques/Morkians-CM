package com.example.casa.TheMorkians;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Created by PedroMarques on 04-01-2016.
 */
public class FinishScene extends BaseScene {
    private Sprite loadImage;
    private Text scoreText;
    private Text finishText;


    @Override
    public void createScene()
    {
        loadImage = new Sprite(0, 0, resourcesManager.loadMenuRegion, vbom);
        loadImage.setScale(1f);
        loadImage.setPosition(400, 240);
        attachChild(loadImage);

        scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("Score: " + resourcesManager.score);

        attachChild(scoreText);

        finishText = new Text(100, 420, resourcesManager.font2, "So close..\nUnfortunately you failed \nand Earth was destroyed.\nBetter luck next time!", new TextOptions(HorizontalAlign.CENTER), vbom);
        finishText.setPosition(400, 180);
        finishText.setText("So close..\bUnfortunately you failed \nand Earth was destroyed.\nBetter luck next time!");

        attachChild(finishText);

    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
        resourcesManager.levelMusic.stop();
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_FINISH;
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
