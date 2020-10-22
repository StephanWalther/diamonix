package tools.state.singleState;

import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

public class State<S extends State<S, D>, D> implements Common {
    public Class<? extends S> nextState = null;
    public D nextStateData = null;
    
    @Override
    public boolean processInput(Input input) {
        return false;
    }
    
    @Override
    public void update(float dt) {}
    
    @Override
    public void draw(Screen screen) {}
    
    public void enter(S lastState, D data) {}
}
