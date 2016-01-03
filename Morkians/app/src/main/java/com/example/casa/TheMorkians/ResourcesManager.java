package com.example.casa.TheMorkians;

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

    public ITextureRegion gameBackgroundRegion;

    public ITextureRegion gameVirtualJoystickBaseRegion;
    public ITextureRegion gameVirtualJoystickPadRegion;

    public ITextureRegion gameSpaceStationRegion;
    public ITextureRegion gamePlayerRegion;
    public ITextureRegion gameBomberRegion;
    public ITextureRegion gameHeavyBomberRegion;
    public ITextureRegion gameKamikazeRegion;
    public ITextureRegion gamePowerUpShootRegion;
    public ITextureRegion gamePowerUpLifeRegion;
    public ITextureRegion gamePlayerLaserRegion;
    public ITextureRegion gameEnemyLaserRegion;
    public ITextureRegion gameMissileRegion;

    private BitmapTextureAtlas loadMenuTextureAtlas;
    private BitmapTextureAtlas MenuTextureAtlas;
    private BitmapTextureAtlas gameTextureAtlas;
    private BitmapTextureAtlas gameVirtualJoystickAtlas;
    private BitmapTextureAtlas gameLevelAtlas;

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
        MenuTextureAtlas.load();
    }

    private void loadMenuAudio()
    {
        //TODO Load da musica do menu
    }

    private void loadMenuFonts()
    {
        FontFactory.setAssetBasePath("font/");
        font = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(),
                256, 256,
                TextureOptions.BILINEAR,
                activity.getAssets(), "Plok.ttf", 18, true, android.graphics.Color.GREEN);
        font.load();

    }

    private void loadGameGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTextureAtlas =  new BitmapTextureAtlas (activity.getTextureManager(), 8192, 600, TextureOptions.BILINEAR );
        gameBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "universe.png", 0, 0);
        gameTextureAtlas.load();

        gameLevelAtlas = new BitmapTextureAtlas (activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR );
        gameSpaceStationRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "spaceStation.png", 0, 0);
        gamePlayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "nave.png", 441, 0);
        gameBomberRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "bombardeiro.png", 441, 39);
        gameHeavyBomberRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "caa.png", 441, 131);
        gameKamikazeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "kamikaze.png", 441, 238);
        gamePowerUpShootRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "PowerUp-Bala.png", 441, 307);
        gamePowerUpLifeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "PowerUp-Vida.png", 441, 358);
        gamePlayerLaserRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "balaplayer.png", 441, 417);
        gameEnemyLaserRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "balainimigo.png", 441, 409);
        gameMissileRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameLevelAtlas,
                activity, "missil.png", 441, 425);
        gameLevelAtlas.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/controls/");
        gameVirtualJoystickAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        gameVirtualJoystickBaseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameVirtualJoystickAtlas,
                activity, "virtual_joystick_base.png", 0, 0);
        gameVirtualJoystickPadRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameVirtualJoystickAtlas,
                activity, "virtual_joystick_pad.png", 128, 0);
        gameVirtualJoystickAtlas.load();
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
        gameLevelAtlas.unload();
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
