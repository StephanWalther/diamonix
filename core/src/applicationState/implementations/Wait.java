package applicationState.implementations;

import java.util.*;

import applicationState.keys.*;
import applicationState.keys.data.*;
import applicationState.state.*;
import core.*;
import tools.progressLine.Timer;
import tools.state.stackState.*;

public class Wait extends ComponentApplicationState<Timer> {
    private WaitData waitData = null;
    
    public Wait(Core core) {
        super(core, new Timer(1).setTime(1).increase());
    }
    
    @Override
    public void update(float dt) {
        super.update(dt);
        if (waitData.progressLineInfo.getProgress() <= waitData.pushProgress) {
            requestStatePopPush(new PopPushRequest<ApplicationStateKey>(
              1, Collections.singletonList(waitData.applicationStateKey)));
        }
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        waitData = applicationStateKey.getWaitData();
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        waitData.applicationStateKey = applicationStateKey;
        return true;
    }
    
    @Override
    public Class<WaitKey> getApplicationStateKeyClass() {
        return WaitKey.class;
    }
}
