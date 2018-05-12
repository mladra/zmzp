package pl.lodz.p.it.wks.wksrecruiter.utils;

import pl.lodz.p.it.wks.wksrecruiter.collections.AttemptAnswer;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

public class ValidatorUtil {

    public static void validateTestAttemptEvaluation(TestAttempt testAttempt) throws WKSRecruiterException {
        if (testAttempt.getScore() < 0 || testAttempt.getScore() > testAttempt.getMaxPoints()) {
            throw WKSRecruiterException.createTestNotEvaluatedException();
        }
    }

    public static void validateTestAttemptAnswersEvaluation(TestAttempt testAttempt) throws WKSRecruiterException {
        for (AttemptAnswer attemptAnswer : testAttempt.getAnswers()) {
            if (attemptAnswer.getScore() < 0 || attemptAnswer.getScore() > attemptAnswer.getMaxPoints()) {
                throw WKSRecruiterException.createTestNotEvaluatedException();
            }
        }
    }
}
