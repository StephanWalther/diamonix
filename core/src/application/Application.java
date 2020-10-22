package application;

import com.badlogic.gdx.*;

import java.util.*;

import application.timeHandler.*;
import applicationState.keys.*;
import applicationState.keys.data.*;
import applicationState.state.*;
import core.*;
import core.objects.screen.*;
import core.objects.worldButton.*;
import tools.input.input.Input;
import tools.input.input.*;
import tools.state.stackState.*;

public class Application implements ApplicationStateStackRequestHandler {
    private final TimeHandler timeHandler = new ReleaseTimeHandler();
    private final Core core;
    private final InputQueue inputQueue;
    private final ApplicationStateStack applicationStateStack;
    
    public Application(OnlineButtonHandler onlineButtonHandler) {
        core = new Core(onlineButtonHandler);
        inputQueue = new InputQueue(core.screen.camera());
        LibGDXInitialization.init(inputQueue);
        applicationStateStack = new ApplicationStateStack(
          this, core,
          Arrays.asList(new MenuKey(), new CurtainKey(new CurtainData(true))));
        buildStateLifecycleListener();
        prepareAllOtherPushes();
    }
    
    private void buildStateLifecycleListener() {
        applicationStateStack.addStateLifecycleListener(
          new StateLifecycleListener<ApplicationStateKey>() {
              @Override
              public void statePushedOnTop(ApplicationStateKey key) {
                  if (key.getClass() == TrueFalseQuestionKey.class) return;
                  if (key.getClass() == GameKey.class) prepareGamePush();
                  else if (key.getClass() == WaitKey.class) {
                      WaitKey waitKey = (WaitKey) key;
                      if (waitKey.getWaitData().applicationStateKey.getClass() == GameKey.class) {
                          prepareGamePush();
                      } else prepareAllOtherPushes();
                  } else handleAllOtherPushes();
              }
          });
    }
    
    private void prepareGamePush() {
        core.title.despawn();
    }
    
    private void prepareAllOtherPushes() {
        core.audio.musicPlayer.setFadeOutTime(2.f);
        core.audio.musicPlayer.setFadeInTime(4.f);
        core.audio.musicPlayer.playMusic("menu");
    }
    
    private void handleAllOtherPushes() {
        prepareAllOtherPushes();
        core.title.spawn();
    }
    
    void resize(int width, int height) {
        core.screen.resize(width, height);
        inputQueue.addResize(core.screen.sizes().getWorldWidth(),
          core.screen.sizes().getWorldHeight());
    }
    
    void progress(float timeTillLastCall) {
        input();
        update(timeTillLastCall);
        render();
    }
    
    private void input() {
        for (Input input = inputQueue.poll(); input != null; input = inputQueue.poll()) {
            timeHandler.processInput(input);
            if (!applicationStateStack.processInput(input)) {
                core.background.processInput(input);
            }
        }
    }
    
    private void update(float timeTillLastCall) {
        int updateCallCount = timeHandler.getUpdateCallCount(timeTillLastCall);
        float dt = timeHandler.getDt();
        for (int i = 0; i < updateCallCount; i++) {
            core.audio.musicPlayer.update(dt);
            core.background.update(dt);
            core.title.update(dt);
            applicationStateStack.update(dt);
        }
    }
    
    private void render() {
        core.screen.clear();
        core.screen.begin(Drawing.instance);
        core.screen.draw(core.background);
        core.screen.draw(core.title);
        core.screen.draw(applicationStateStack);
        core.screen.draw(core.fps);
        core.screen.end();
    }
    
    void pause() {
        core.files.saveSettings(core.audio.volume, core.fps);
    }
    
    void resume() {
        applicationStateStack.processInput(new Reset());
    }
    
    void dispose() {
        core.assets.dispose();
        core.screen.dispose();
    }
    
    @Override
    public void handleApplicationExitRequest() {
        Gdx.app.exit();
    }
}
