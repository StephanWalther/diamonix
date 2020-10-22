package applicationState.keys;

import core.*;
import applicationState.implementations.*;
import applicationState.keys.data.*;
import applicationState.state.*;

public class WaitKey extends ApplicationStateKey {
    private final WaitData waitData;
    
    public WaitKey(WaitData waitData) {
        this.waitData = waitData;
    }
    
    @Override
    public WaitData getWaitData() {
        return waitData;
    }
    
    @Override
    protected ApplicationState create(final Core core) {
        return new Wait(core);
    }
}
