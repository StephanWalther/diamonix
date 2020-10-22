package application;

import com.badlogic.gdx.*;

import core.objects.worldButton.*;

public class LibGDXBridge implements ApplicationListener {
    private final OnlineButtonHandler onlineButtonHandler;
    private Application application;
    
    public LibGDXBridge(OnlineButtonHandler onlineButtonHandler) {
        this.onlineButtonHandler = onlineButtonHandler;
    }
    
    @Override
    public void create() {
        application = new Application(onlineButtonHandler);
    }
    
    @Override
    public void resize(int width, int height) {
        application.resize(width, height);
    }
    
    @Override
    public void render() {
        application.progress(Gdx.graphics.getDeltaTime());
    }
    
    @Override
    public void pause () {
        application.pause();
    }
    
    @Override
    public void resume () {
        application.resume();
    }
    
    @Override
    public void dispose() {
        application.dispose();
    }
}
