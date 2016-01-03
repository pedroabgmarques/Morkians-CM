package com.example.casa.TheMorkians;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Paulo on 02-01-2016.
 */
public class Enemy extends Sprite
{
    private int vida;
    public int positionX, positionY;
    private Bala novaBala;
    ResourcesManager resourcesManager;

    public Enemy(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ResourcesManager rManager)
    {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

        vida=2;
        positionX=(int)pX;
        positionY=(int)pY;
        resourcesManager=rManager;
        shoot();
    }

    public void shoot()
    {
        TimerHandler timerHandler = new TimerHandler(3, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        //criar nova bala.
                        novaBala=new Bala(getX(), getY(), resourcesManager.gameEnemyLaserRegion,getVertexBufferObjectManager());
                        attachChild(novaBala);
                    }
                });
        registerUpdateHandler(timerHandler);
    }

}
