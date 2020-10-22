package tools.state.singleState;

import java.util.*;

import tools.common.*;
import tools.input.input.*;
import tools.input.processors.*;
import tools.screen.*;

public class StateController<S extends State<S, D>, D>
  extends DefaultInputProcessor implements Common {
    protected final Map<Class<? extends S>, S> statePool
      = new HashMap<Class<? extends S>, S>();
    protected S currentState;
    
    @Override
    public boolean processInput(Input input) {
        boolean result = currentState.processInput(input) || super.processInput(input);
        handleStateChangeRequests();
        return result;
    }
    
    @Override
    public void update(float dt) {
        currentState.update(dt);
        handleStateChangeRequests();
    }
    
    protected void setCurrentState(Class<? extends S> state) {
        currentState = statePool.get(state);
        currentState.enter(null, null);
        handleStateChangeRequests();
    }
    
    private void handleStateChangeRequests() {
        Class<? extends S> nextState = currentState.nextState;
        while (nextState != null) {
            S lastState = currentState;
            D data = currentState.nextStateData;
            lastState.nextState = null;
            lastState.nextStateData = null;
            currentState = statePool.get(nextState);
            currentState.enter(lastState, data);
            notifyStateEntered(currentState);
            nextState = currentState.nextState;
        }
    }
    
    protected void notifyStateEntered(S state) {}
    
    @Override
    public void draw(Screen screen) {
        screen.draw(currentState);
    }
}
