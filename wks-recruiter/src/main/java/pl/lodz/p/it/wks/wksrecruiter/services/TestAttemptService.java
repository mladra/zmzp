package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.security.core.Authentication;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;
import java.util.Map;

public interface TestAttemptService {
    Iterable<TestAttempt> getTestAttempts(Authentication authentication) throws WKSRecruiterException;

    Map<String, Collection<TestAttempt>> getAllTestsAttempts(Authentication authentication) throws WKSRecruiterException;

    TestAttempt evaluateTestAttempt(String login, TestAttempt testAttempt, Authentication authentication) throws WKSRecruiterException;
}
