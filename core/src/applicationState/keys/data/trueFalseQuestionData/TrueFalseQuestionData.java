package applicationState.keys.data.trueFalseQuestionData;

import applicationState.implementations.*;

public class TrueFalseQuestionData {
    public final TrueFalseQuestion.Question question;
    public final TrueFalseQuestionListener trueFalseQuestionListener;

    public TrueFalseQuestionData(TrueFalseQuestion.Question question,
                                 TrueFalseQuestionListener trueFalseQuestionListener) {
        this.question = question;
        this.trueFalseQuestionListener = trueFalseQuestionListener;
    }
}
