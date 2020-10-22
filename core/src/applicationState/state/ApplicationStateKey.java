package applicationState.state;

import applicationState.keys.data.trueFalseQuestionData.*;
import core.*;
import applicationState.keys.data.*;

public abstract class ApplicationStateKey {
    protected abstract ApplicationState create(Core core);
    
    public CurtainData getCurtainData() {
        return null;
    }
    
    public WaitData getWaitData() {
        return null;
    }
    
    public TrueFalseQuestionData getTrueFalseQuestionData() {
        return null;
    }
    
    public GameData getGameData() {
        return null;
    }
}
