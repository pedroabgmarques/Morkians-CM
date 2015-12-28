package com.example.casa.TheMorkians;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Victor on 26/12/2015.
 */
public class Player extends SimpleBaseGameActivity
        implements IOnSceneTouchListener
{

    private float posicaoX, posicaoY;
    private int vidas;
    private BitmapTextureAtlas bitMapTextureAtlas;
    private TextureRegion playerRegion;
    private Sprite playerSpite;
    private Scene scene;


    @Override
    protected void onCreateResources() throws IOException
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        bitMapTextureAtlas = new BitmapTextureAtlas(getTextureManager(),256, 256);
        playerRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitMapTextureAtlas,this,"nave.png",0,0);

        bitMapTextureAtlas.load();

    }

    @Override
    protected Scene onCreateScene() {

        scene= new Scene();
        playerSpite= new Sprite(posicaoX,posicaoY,playerRegion,getVertexBufferObjectManager());

        scene.attachChild(playerSpite);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        return null;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        return false;
    }
}

