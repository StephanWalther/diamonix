package core.objects.screen;

import com.badlogic.gdx.*;

public class Sizes implements tools.screen.Sizes {
    private int screenWidth;
    private int screenHeight;
    private WorldSize worldSize = null;
    private int worldWidth;
    private int worldHeight;
    private float inversWorldToScreenScaleFactor;
    
    Sizes() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        calculateIdealWorldSize(width, height);
        resize(width, height);
    }
    
    private void calculateIdealWorldSize(int width, int height) {
        for (int i = 0; i < WorldSize.values().length - 1; i++) {
            worldSize = WorldSize.values()[i];
            if (fits(width, height)) return;
        }
        worldSize = WorldSize.values()[WorldSize.values().length - 1];
    }

    private boolean fits(int width, int height) {
        return worldSize.idealWidth() <= width && worldSize.idealHeight() <= height;
    }
    
    void resize(int width, int height) {
        if (screenWidth == width && screenHeight == height) return;
        screenWidth = width;
        screenHeight = height;
        calculateWorldSize();
        calculateInversWorldToScreenScaleFactor();
    }
    
    private void calculateWorldSize() {
        float windowRatio = (float) screenWidth/(float) screenHeight;
        float optimalRatio = (float) worldSize.idealWidth()/(float) worldSize.idealHeight();
        if (windowRatio <= optimalRatio) {
            worldWidth = worldSize.idealWidth();
            worldHeight = Math.min((int) (worldSize.maxHeight()),
              (int) ((float) (worldWidth)/windowRatio + 0.5f));
        } else {
            worldHeight = worldSize.idealHeight();
            worldWidth = Math.min((int) (worldSize.maxWidth()),
              (int) ((float) (worldHeight)*windowRatio + 0.5f));
        }
    }
    
    private void calculateInversWorldToScreenScaleFactor() {
        float widthScaleFactor = (float) worldWidth/(float) screenWidth;
        float heightScaleFactor = (float) worldHeight/(float) screenHeight;
        inversWorldToScreenScaleFactor = Math.max(widthScaleFactor, heightScaleFactor);
    }
    
    @Override
    public int getScreenWidth() {
        return screenWidth;
    }
    
    @Override
    public int getScreenHeight() {
        return screenHeight;
    }
    
    @Override
    public int getIdealWorldWidth() {
        return worldSize.idealWidth();
    }
    
    @Override
    public int getIdealWorldHeight() {
        return worldSize.idealHeight();
    }
    
    @Override
    public int getWorldWidth() {
        return worldWidth;
    }
    
    @Override
    public int getWorldHeight() {
        return worldHeight;
    }
    
    @Override
    public int getMaxWorldWidth() {
        return worldSize.maxWidth();
    }
    
    @Override
    public int getMaxWorldHeight() {
        return worldSize.maxHeight();
    }
    
    public WorldSize getWorldSize() {
        return worldSize;
    }
    
    float getInversWorldToScreenScaleFactor() {
        return inversWorldToScreenScaleFactor;
    }
}
