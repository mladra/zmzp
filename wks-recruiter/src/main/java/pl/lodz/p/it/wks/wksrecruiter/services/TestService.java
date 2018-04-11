package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;

public interface TestService {
    Test addPositionsToTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test removePositionsFromTest(Collection<String> positionNames, String testId) throws WKSRecruiterException;

    Test deleteTest(String testId) throws WKSRecruiterException;

    Iterable<Test> getTests();
	
	Test getTestById(String testId) throws WKSRecruiterException;
}
