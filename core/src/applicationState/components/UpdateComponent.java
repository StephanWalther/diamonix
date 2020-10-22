package applicationState.components;

import java.util.*;

import applicationState.state.*;
import tools.common.*;

public class UpdateComponent extends ApplicationStateComponent {
    private final List<Updatable> updatables = new ArrayList<Updatable>();
    
    public void add(Updatable updatable) {
        updatables.add(updatable);
    }
    
    @Override
    public void update(float dt) {
        UpdateDistributer.updateAll(updatables, dt);
    }
}
