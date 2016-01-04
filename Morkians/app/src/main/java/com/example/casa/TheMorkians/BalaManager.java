package com.example.casa.TheMorkians;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

/**
 * Created by Pedro Marques on 04-01-2016.
 */
public class BalaManager {
    private static ArrayList<Bala> listaBalasPlayerVivas;
    private static ArrayList<Bala> listaBalasPlayerMortas;
    private static ArrayList<Bala> listaBalasEnemyVivas;
    private static ArrayList<Bala> listaBalasEnemyMortas;

    private static ArrayList<Bala> listaBalasRemover;

    private static ResourcesManager resourcesManager;
    private static VertexBufferObjectManager vbom;
    private static Camera camera;
    private static Bala bala;

    public static void Initialize(){
        resourcesManager = ResourcesManager.getInstance();
        vbom = resourcesManager.vbom;
        camera = resourcesManager.camera;

        listaBalasEnemyMortas = new ArrayList<Bala>();
        listaBalasEnemyVivas = new ArrayList<Bala>();
        listaBalasPlayerMortas = new ArrayList<Bala>();
        listaBalasPlayerVivas = new ArrayList<Bala>();
        listaBalasRemover = new ArrayList<Bala>();

        //criar à partida 100 balas
        for(int i = 0; i < 100; i++){
            //Balas do inimigo
            bala = new Bala(0, 0, ResourcesManager.getInstance().gameEnemyLaserRegion, vbom, camera);
            listaBalasEnemyMortas.add(bala);

            //Balas do player
            bala = new Bala(0, 0, ResourcesManager.getInstance().gamePlayerLaserRegion, vbom, camera);
            listaBalasPlayerMortas.add(bala);
        }
    }

    public static ArrayList<Bala> getBalasPlayer(){
        return listaBalasPlayerVivas;
    }

    public static ArrayList<Bala> getBalasInimigo(){
        return listaBalasEnemyVivas;
    }

    public static Bala shootBalaInimigo(float pX, float pY){
        bala = listaBalasEnemyMortas.get(0);
        listaBalasEnemyMortas.remove(bala);

        Log.v("debug", "Bala adicionada!");
        Log.v("debug", "Vivas: "+ listaBalasEnemyVivas.size());
        Log.v("debug", "Mortas: " + listaBalasEnemyMortas.size());

        bala.resetEntityModifiers();
        bala.setPosition(pX, pY);
        MoveXModifier moveXModifier = new MoveXModifier(10f, pX + camera.getCenterX() - camera.getWidth() / 2, -camera.getWidth() );
        bala.registerEntityModifier(moveXModifier);
        listaBalasEnemyVivas.add(bala);
        bala.setScale(0.5f);
        return bala;
    }

    public static Bala shootBalaPlayer(float pX, float pY){
        bala = listaBalasPlayerMortas.get(0);
        listaBalasPlayerMortas.remove(bala);
        bala.resetEntityModifiers();
        bala.setPosition(pX, pY);
        MoveXModifier moveXModifier = new MoveXModifier(10f, pX + camera.getCenterX() - camera.getWidth() / 2, camera.getWidth() );
        bala.registerEntityModifier(moveXModifier);
        listaBalasPlayerVivas.add(bala);
        bala.setScale(0.5f);
        return bala;
    }

    public static void RemoverBalaEnemy(Bala bala){
        listaBalasEnemyVivas.remove(bala);
        listaBalasEnemyMortas.add(bala);
    }

    public static void RemoverBalaPlayer(Bala bala){
        listaBalasPlayerVivas.remove(bala);
        listaBalasPlayerMortas.add(bala);
    }

    public static void RemoveBalas(){

        listaBalasRemover.clear();
        for(Bala bala: listaBalasEnemyVivas){
            if(bala.getX()<=camera.getCenterX()-camera.getWidth()/2){
                listaBalasRemover.add(bala);
            }
        }

        for(Bala bala: listaBalasRemover){
            listaBalasEnemyVivas.remove(bala);
            listaBalasEnemyMortas.add(bala);
        }

        listaBalasRemover.clear();
        for(Bala bala: listaBalasPlayerVivas){
            if(bala.getX()>=camera.getCenterX()-camera.getWidth()/2){
                listaBalasRemover.add(bala);
            }
        }

        for(Bala bala: listaBalasRemover){
            listaBalasPlayerVivas.remove(bala);
            listaBalasPlayerMortas.add(bala);
        }

    }
}
