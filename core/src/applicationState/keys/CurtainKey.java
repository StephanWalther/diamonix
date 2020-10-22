package applicationState.keys;

import core.*;
import applicationState.implementations.*;
import applicationState.keys.data.*;
import applicationState.state.*;

public class CurtainKey extends ApplicationStateKey {
    private final CurtainData curtainData;
    
    public CurtainKey(CurtainData curtainData) {
        this.curtainData = curtainData;
    }
    
    protected ApplicationState create(final Core core) {
        return new Curtain(core);
    }
    
    @Override
    public CurtainData getCurtainData() {
        return curtainData;
    }
}
