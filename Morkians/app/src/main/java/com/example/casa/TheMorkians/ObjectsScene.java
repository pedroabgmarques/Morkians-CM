package com.example.casa.TheMorkians;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Victor on 03/01/2016.
 */
public class ObjectsScene extends Sprite
{
    public ObjectsScene(float posX,float posY,ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager)
    {

        super (posX,posY,pTextureRegion,pVertexBufferObjectManager);
    }

}
