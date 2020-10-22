package core.objects.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.*;

public class VirtualScreen {
    private FrameBuffer[] frameBuffers = new FrameBuffer[2];
    private int currentBuffer = 0;
    private boolean isActive = false;
    
    public VirtualScreen(int width, int height) {
        resize(width, height);
    }
    
    public void resize(int width, int height) {
        for (int i = 0; i < 2; i++) {
            if (frameBuffers[i] != null) frameBuffers[i].dispose();
            frameBuffers[i] = new FrameBuffer(Pixmap.Format.RGB888, width, height,false);
        }
    }
    
    public int getWidth() {
        return frameBuffers[0].getWidth();
    }
    
    public int getHeight() {
        return frameBuffers[0].getHeight();
    }
    
    void begin() {
        isActive = true;
        frameBuffers[currentBuffer].begin();
    }
    
    public Texture takeTexture() {
        Texture texture;
        if (!isActive) frameBuffers[currentBuffer].begin();
        texture = frameBuffers[currentBuffer].getColorBufferTexture();
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        switchBuffers();
        clearCurrentScreen();
        if (!isActive) frameBuffers[currentBuffer].end();
        return texture;
    }
    
    private void switchBuffers() {
        frameBuffers[currentBuffer].end();
        currentBuffer = (currentBuffer + 1) % 2;
        frameBuffers[currentBuffer].begin();
    }
    
    private void clearCurrentScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
    
    void end() {
        frameBuffers[currentBuffer].end();
        isActive = false;
    }
    
    public void dispose() {
        for (FrameBuffer frameBuffer : frameBuffers) {
            if (frameBuffer != null) frameBuffer.dispose();
        }
    }
}
