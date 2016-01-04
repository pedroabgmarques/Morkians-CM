package com.example.casa.TheMorkians;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

/**
 * Created by Paulo on 03-01-2016.
 */
public class Bala extends Sprite
{

    private float speed;
    int duration;


    public Bala(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager,Camera camera) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);


        speed=40f;
        Random random = new Random();
        duration = random.nextInt();

        MoveXModifier moveXModifier = new MoveXModifier(duration * speed,pX,-camera.getWidth() );
        registerEntityModifier(moveXModifier);

    }
}
