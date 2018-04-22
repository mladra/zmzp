package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.security.core.Authentication;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;
import java.util.List;

public interface TestService {
    Test createTest(Test test) throws WKSRecruiterException;

    Test editTest(String testId, Test test) throws WKSRecruiterException;

    Test addPositionsToTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test removePositionsFromTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test deleteTest(String testId) throws WKSRecruiterException;

    Iterable<Test> getTests(String role, Authentication authentication) throws WKSRecruiterException;

    Test getTestById(String testId) throws WKSRecruiterException;

    Test setTestQuestions(String testId, List<QuestionInfo> questions) throws WKSRecruiterException;
}
