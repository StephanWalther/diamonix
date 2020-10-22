package applicationState.keys;

import applicationState.implementations.*;
import applicationState.keys.data.trueFalseQuestionData.*;
import applicationState.state.*;
import core.*;

public class TrueFalseQuestionKey extends ApplicationStateKey {
    private final TrueFalseQuestionData trueFalseQuestionData;
    
    public TrueFalseQuestionKey(TrueFalseQuestionData trueFalseQuestionData) {
        this.trueFalseQuestionData = trueFalseQuestionData;
    }
    
    @Override
    protected ApplicationState create(final Core core) {
        return new TrueFalseQuestion(core);
    }
    
    @Override
    public TrueFalseQuestionData getTrueFalseQuestionData() {
        return trueFalseQuestionData;
    }
}
