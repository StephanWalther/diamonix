package applicationState.components;

import java.util.*;

import applicationState.state.*;
import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

public class CommonComponent extends ApplicationStateComponent {
    private final List<Common> commons = new ArrayList<Common>();
    
    public CommonComponent add(Common common) {
        commons.add(common);
        return this;
    }
    
    @Override
    public boolean processInput(Input input) {
        return InputDistributor.distribute(commons, input);
    }
    
    @Override
    public void update(float dt) {
        UpdateDistributer.updateAll(commons, dt);
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(commons, screen);
    }
}
