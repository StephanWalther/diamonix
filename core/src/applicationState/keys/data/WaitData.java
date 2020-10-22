package applicationState.keys.data;

import applicationState.state.*;
import tools.progressLine.*;

public class WaitData {
    public ApplicationStateKey applicationStateKey;
    public final ProgressLineInfo progressLineInfo;
    public final float pushProgress;
    
    public WaitData(ApplicationStateKey applicationStateKey, ProgressLineInfo progressLineInfo,
                    float pushProgress) {
        this.applicationStateKey = applicationStateKey;
        this.progressLineInfo = progressLineInfo;
        this.pushProgress = pushProgress;
    }
}
