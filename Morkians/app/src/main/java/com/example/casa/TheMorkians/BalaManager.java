package com.example.casa.TheMorkians;

import org.andengine.engine.camera.Camera;
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
    public static ArrayList<Bala> listaBalasDetach;

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
        listaBalasDetach = new ArrayList<Bala>();

        //criar à partida 100 balas
        for(int i = 0; i < 1000; i++){
            //Balas do inimigo
            bala = new Bala(0, 0, ResourcesManager.getInstance().gameEnemyLaserRegion, vbom);
            listaBalasEnemyMortas.add(bala);

            //Balas do player
            bala = new Bala(0, 0, ResourcesManager.getInstance().gamePlayerLaserRegion, vbom);
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
        bala.setPosition(pX, pY);
        bala.setScale(0.5f);
        listaBalasEnemyVivas.add(bala);
        //resourcesManager.soundShoot.play();
        return bala;
    }

    public static Bala shootBalaPlayer(float pX, float pY){
        bala = listaBalasPlayerMortas.get(0);
        listaBalasPlayerMortas.remove(bala);
        bala.setPosition(pX, pY);
        bala.setScale(0.5f);
        listaBalasPlayerVivas.add(bala);
        resourcesManager.soundShoot.play();
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
        listaBalasDetach.clear();
        for(Bala bala: listaBalasEnemyVivas){
            if(bala.getX() <=camera.getCenterX() -camera.getWidth()/2){
                listaBalasRemover.add(bala);
            }
        }

        listaBalasDetach.addAll(listaBalasRemover);

        for(Bala bala: listaBalasRemover){
            listaBalasEnemyVivas.remove(bala);
            listaBalasEnemyMortas.add(bala);
        }

        listaBalasRemover.clear();
        for(Bala bala: listaBalasPlayerVivas){
            if(bala.getX()>=camera.getCenterX()+camera.getWidth()/2){
                listaBalasRemover.add(bala);
            }
        }

        for(Bala bala: listaBalasRemover){
            listaBalasPlayerVivas.remove(bala);
            listaBalasPlayerMortas.add(bala);
        }

        listaBalasDetach.addAll(listaBalasRemover);

    }
}
