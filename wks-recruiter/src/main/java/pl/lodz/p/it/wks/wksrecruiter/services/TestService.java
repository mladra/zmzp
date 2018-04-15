package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;
import java.util.List;

public interface TestService {
    Test addPositionsToTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test removePositionsFromTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test deleteTest(String testId) throws WKSRecruiterException;

    Iterable<Test> getTests();

    Test getTest(String testId) throws WKSRecruiterException;

    Test setTestQuestions(String testId, List<QuestionInfo> questions) throws WKSRecruiterException;
}
