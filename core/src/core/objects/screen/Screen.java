package core.objects.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.*;

import tools.common.*;
import tools.screen.*;

public class Screen implements tools.screen.Screen {
    VirtualScreen virtualScreen;
    private final Sizes sizes = new Sizes();
    private final Drawer drawer = new Drawer();
    private final Camera camera = new Camera(sizes, drawer);
    private final SingleDrawer singleDrawer = new SingleDrawer();
    private ScreenState currentState = null;
    
    public Screen() {
        SizeGetter.sizes = sizes;
    }
    
    @Override
    public Sizes sizes() {
        return sizes;
    }
    
    @Override
    public Camera camera() {
        return camera;
    }
    
    @Override
    public Drawer drawer() {
        return drawer;
    }
    
    public void resize(int width, int height) {
        sizes.resize(width, height);
        if (virtualScreen == null) {
            virtualScreen = new VirtualScreen(sizes.getWorldWidth(), sizes.getWorldHeight());
        } else virtualScreen.resize(sizes.getWorldWidth(), sizes.getWorldHeight());
        camera.resize(sizes);
        camera.update();
    }
    
    public void clear() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
    
    public void begin(ScreenState state) {
        if (currentState != null) currentState.leave(this);
        currentState = state;
        currentState.enter(this);
    }
    
    @Override
    public void draw(Drawable drawable) {
        drawable.draw(this);
    }
    
    @Override
    public void beginModification(ShaderProgram modificationShader) {
        drawer.beginModification(modificationShader);
    }
    
    @Override
    public void modify(Modifier modifier) {
        modifier.apply(this, drawer.getShader());
        Texture texture = virtualScreen.takeTexture();
        singleDraw(texture);
    }
    
    @Override
    public void endModification() {
        drawer.endModification();
    }
    
    public void end() {
        currentState.leave(this);
        currentState = null;
        finalScreenDraw(virtualScreen.takeTexture());
    }
    
    private void finalScreenDraw(Texture texture) {
        drawer.setProjectionMatrix(camera.getScreenCombined());
        singleDraw(texture);
        drawer.setProjectionMatrix(camera.getWorldCombined());
    }
    
    private void singleDraw(Texture texture) {
        drawer.begin();
        draw(singleDrawer.setTexture(texture));
        drawer.end();
    }
    
    public void dispose() {
        virtualScreen.dispose();
        drawer.dispose();
    }
}
