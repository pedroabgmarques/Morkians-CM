package com.example.casa.TheMorkians;

import android.graphics.Typeface;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


/**
 * Created by PedroMarques on 29-12-2015.
 */
public class ResourcesManager {
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    public Font font;

    public ITextureRegion loadMenuRegion;
    public ITextureRegion menuBackgroundRegion;
    public ITextureRegion playRegion;
    public ITextureRegion creditsRegion;
    public ITextureRegion gameBackgroundRegion;

    private BitmapTextureAtlas loadMenuTextureAtlas;
    private BitmapTextureAtlas MenuTextureAtlas;
    private BitmapTextureAtlas gameTextureAtlas;

    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------

    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }

    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }

    private void loadMenuGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        MenuTextureAtlas =  new BitmapTextureAtlas (activity.getTextureManager(), 1024, 1024, TextureOptions. BILINEAR ) ;
        menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "menu_background.png", 112, 0);
        playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "new_game.png", 0, 481);
        creditsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "credits.png", 0, 583);
        MenuTextureAtlas.load();
    }

    private void loadMenuAudio()
    {

    }

    private void loadMenuFonts()
    {
        font = FontFactory.create(activity.getFontManager(), activity.getTextureManager(),
                256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        font.load();
    }

    private void loadGameGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTextureAtlas =  new BitmapTextureAtlas (activity.getTextureManager(), 8192, 600, TextureOptions.BILINEAR );
        gameBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "universe.png", 0, 0);
        gameTextureAtlas.load();
    }

    private void loadGameFonts()
    {

    }

    private void loadGameAudio()
    {

    }

    public void loadSplashScreen()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        loadMenuTextureAtlas =  new BitmapTextureAtlas (activity.getTextureManager(), 800, 480, TextureOptions. BILINEAR ) ;
        loadMenuRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadMenuTextureAtlas, activity, "splashscreen.png", 0, 0);
        loadMenuTextureAtlas.load() ;

    }

    public void unloadSplashScreen()
    {
        loadMenuTextureAtlas.unload();
        loadMenuRegion = null;

    }

    public void unloadMenuTextures()
    {
        MenuTextureAtlas.unload();
    }

    public void loadMenuTextures()
    {
        MenuTextureAtlas.load();
    }

    public void unloadGameTextures()
    {
        // TODO (Since we did not create any textures for game scene yet)
    }

    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
}
