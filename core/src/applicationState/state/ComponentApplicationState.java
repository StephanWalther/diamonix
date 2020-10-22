package applicationState.state;

import java.util.*;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.keys.data.*;
import core.*;
import tools.common.*;
import tools.input.input.*;
import tools.keyCheck.*;
import tools.progressLine.*;
import tools.screen.*;
import tools.state.stackState.*;

public abstract class ComponentApplicationState<P extends ProgressLine>
  extends ComponentState<ApplicationStateKey, ApplicationStateRequestHandler,
                            ApplicationStateComponent>
  implements ApplicationState, ApplicationStateRequestHandler {
    protected final Core core;
    protected final P stateSpawnProgressLine;
    
    protected ComponentApplicationState(Core core, P stateSpawnProgressLine) {
        this.stateSpawnProgressLine = stateSpawnProgressLine;
        this.core = core;
    }
    
    protected void buildBackKeyComponent() {
        Action keyAction = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new MenuKey());
            }
        };
        buildBackKeyComponent(keyAction);
    }
    
    protected void buildBackKeyComponent(final Action keyAction) {
        addComponent(new KeyComponent(IsBackKey.instance, keyAction));
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        return false;
    }
    
    @Override
    public void update(float dt) {
        stateSpawnProgressLine.update(dt);
        super.update(dt);
        updateStateBelow(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        drawStateBelow(screen);
        super.draw(screen);
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        stateSpawnProgressLine.increase();
        processInputStateBelow(new Reset());
    }
    
    @Override
    protected void setRequestHandlerForAddedComponent(ApplicationStateComponent
                                                        applicationStateComponent) {
        applicationStateComponent.setRequestHandler(this);
    }
    
    @Override
    public ProgressLineInfo getSpawnProgressLineInfo() {
        return stateSpawnProgressLine;
    }
    
    @Override
    public void handleApplicationExitRequest() {
        requestApplicationExit();
    }
    
    protected void requestApplicationExit() {
        requestHandler.handleApplicationExitRequest();
    }
    
    protected void scheduleStatePush(ApplicationStateKey applicationStateKey) {
        scheduleStatePush(applicationStateKey, 0.5f);
    }
    
    protected void scheduleStatePush(ApplicationStateKey applicationStateKey, float pushProgress) {
        stateSpawnProgressLine.decrease();
        if (stateSpawnProgressLine.getProgress() <= pushProgress) {
            requestStatePopPush(new PopPushRequest<ApplicationStateKey>(
              0, Collections.singletonList(applicationStateKey)));
        } else {
            WaitData waitData = new WaitData(applicationStateKey, stateSpawnProgressLine,
              pushProgress);
            WaitKey waitKey = new WaitKey(waitData);
            requestStatePopPush(new PopPushRequest<ApplicationStateKey>(
              0, Collections.singletonList((ApplicationStateKey) waitKey)));
        }
    }
}
