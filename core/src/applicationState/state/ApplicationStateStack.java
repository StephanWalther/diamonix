package applicationState.state;

import java.util.*;

import application.*;
import core.*;
import tools.progressLine.*;
import tools.state.stackState.*;

public class ApplicationStateStack
  extends StateStack<ApplicationStateKey, ApplicationStateRequestHandler, ApplicationState,
                      StateLifecycleListener<ApplicationStateKey>>
  implements ExternalStateChangeRequestHandler, ApplicationStateRequestHandler {
    private final ApplicationStateStackRequestHandler applicationStateStackRequestHandler;
    private final Core core;
    private final Map<Class<? extends ApplicationStateKey>, ApplicationState> gameStatePool
      = new HashMap<Class<? extends ApplicationStateKey>, ApplicationState>();
    
    public ApplicationStateStack(
      ApplicationStateStackRequestHandler applicationStateStackRequestHandler,
      Core core, List<ApplicationStateKey> initialStates) {
        this.applicationStateStackRequestHandler = applicationStateStackRequestHandler;
        this.core = core;
        core.background.setExternalStateChangeRequestHandler(this);
        pushStatesOnTop(initialStates);
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey stateKey) {
        return !stack.isEmpty() &&
                 stack.lastElement().getApplicationStateKeyClass() != stateKey.getClass() &&
                 stack.lastElement().handleExternalStateChangeRequest(stateKey);
    }
    
    @Override
    public void update(float dt) {
        super.update(dt);
        for (int i = 0; i < stack.size(); i++) {
            ProgressLineInfo spawnProgressLineInfo = stack.get(i).getSpawnProgressLineInfo();
            if (spawnProgressLineInfo.getProgress() == 0 && !spawnProgressLineInfo.isIncreasing()) {
                statePopped(removeState(i));
                i--;
            }
        }
    }
    
    private ApplicationState removeState(int i) {
        ApplicationState poppedState = stack.remove(i);
        poppedState.setStateBelow(null);
        poppedState.popped();
        poppedState.setRequestHandler(null);
        if (i > 0) stack.get(i - 1).stateAbovePopped();
        if (i < stack.size()) {
            ApplicationState stateBelow = i > 0 ? stack.get(i - 1) : null;
            stack.get(i).setStateBelow(stateBelow);
            stack.get(i).stateBelowPopped();
        }
        return poppedState;
    }
    
    @Override
    protected ApplicationState getState(ApplicationStateKey gameStateKey) {
        ApplicationState applicationState = gameStatePool.remove(gameStateKey.getClass());
        if (applicationState == null) {
            for (int i = 0; i < stack.size(); i++) {
                ApplicationState as = stack.get(i);
                if (as.getApplicationStateKeyClass() == gameStateKey.getClass()) {
                    applicationState = as;
                    removeState(i);
                    break;
                }
            }
            if (applicationState == null) applicationState = gameStateKey.create(core);
        }
        applicationState.setRequestHandler(this);
        return applicationState;
    }
    
    @Override
    protected void statePopped(ApplicationState applicationState) {
        gameStatePool.put(applicationState.getApplicationStateKeyClass(), applicationState);
    }
    
    @Override
    public void handleApplicationExitRequest() {
        applicationStateStackRequestHandler.handleApplicationExitRequest();
    }
}
