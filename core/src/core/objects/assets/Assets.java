package core.objects.assets;

import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

import tools.*;
import tools.component.graphic.*;

public final class Assets {
    private final AssetManager assetManager = new AssetManager();
    private final Origins origins = new Origins();
    private final String texturePath;
    private final String fontFolder;
    private final String soundFolder;
    private final String musicFolder;
    
    public Assets(String worldSize) {
        texturePath = "textures/" + worldSize + "/all.atlas";
        fontFolder = "fonts/" + worldSize;
        soundFolder = "sounds";
        musicFolder = "music";
        loadTextures();
        loadFonts();
        loadSounds();
        loadMusic();
        assetManager.finishLoading();
        setTextureFilters();
    }
    
    private void setTextureFilters() {
        for (Texture texture : assetManager.get(texturePath, TextureAtlas.class).getTextures()) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        for (FileHandle fileHandle : Tools.getInternalFileHandle(fontFolder).list("fnt")) {
            Array<TextureRegion> textureRegions
              = assetManager.get(fileHandle.path(), BitmapFont.class).getRegions();
            for (TextureRegion textureRegion : textureRegions) {
                textureRegion.getTexture().setFilter(
                  Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
    }
    
    public void update() {
        assetManager.update();
    }
    
    public float getProgress() {
        return assetManager.getProgress();
    }
    
    private void loadTextures() {
        assetManager.load(texturePath, TextureAtlas.class);
    }
    
    // On Samsung Galaxy S3 mini (Samsumg GT-18200 (Android 4.2.2, API 17))
    // there is somehow an extra file in the "sounds" folder. Hence, we
    // need to ensure that only files which ended with "mp3" are loaded.
    // For consistency we added the corresponding suffixes also for
    // fonts and music.
    private void loadFonts() {
        for (FileHandle fileHandle : Tools.getInternalFileHandle(fontFolder).list("fnt")) {
            assetManager.load(fileHandle.path(), BitmapFont.class);
        }
    }
    
    private void loadSounds() {
        for (FileHandle fileHandle : Tools.getInternalFileHandle(soundFolder).list("mp3")) {
            assetManager.load(fileHandle.path(), Sound.class);
        }
    }
    
    private void loadMusic() {
        for (FileHandle fileHandle : Tools.getInternalFileHandle(musicFolder).list("mp3")) {
            assetManager.load(fileHandle.path(), Music.class);
        }
    }
    
    public TextureOrigin getTextureOrigin(String string) {
        Array<TextureAtlas> textureAtlases
          = assetManager.getAll(TextureAtlas.class, new Array<TextureAtlas>());
        for (TextureAtlas textureAtlas : textureAtlases) {
            TextureRegion textureRegion = textureAtlas.findRegion(string);
            if (textureRegion != null) {
                return new TextureOrigin(textureRegion, origins.getOrigin(string));
            }
        }
        Tools.nullCheck(null, string);
        // Is never reached.
        return null;
    }
    
    public BitmapFont getFont(String string) {
        String fileName = fontFolder + "/" + string + ".fnt";
        BitmapFont font = assetManager.get(fileName, BitmapFont.class);
        Tools.nullCheck(font, string);
        return font;
    }
    
    public Sound getSound(String string) {
        Sound sound = assetManager.get(soundFolder + "/" + string + ".mp3", Sound.class);
        Tools.nullCheck(sound, string);
        return sound;
    }
    
    public Music getMusic(String string) {
        Music music = assetManager.get(musicFolder + "/" + string + ".mp3", Music.class);
        Tools.nullCheck(music, string);
        return music;
    }
    
    public void dispose() {
        assetManager.dispose();
    }
}
