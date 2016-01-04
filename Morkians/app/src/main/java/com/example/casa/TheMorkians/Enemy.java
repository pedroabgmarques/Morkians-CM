package com.example.casa.TheMorkians;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Paulo on 02-01-2016.
 */
public class Enemy extends Sprite
{
    private int vida;

    private Bala novaBala;
    ResourcesManager resourcesManager;

    public Enemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ResourcesManager rManager)
    {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

        vida=2;

        resourcesManager=rManager;

    }

}
