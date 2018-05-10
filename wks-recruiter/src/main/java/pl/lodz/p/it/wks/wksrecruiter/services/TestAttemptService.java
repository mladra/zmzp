package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.security.core.Authentication;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

public interface TestAttemptService {
    Iterable<TestAttempt> getTestAttempts(Authentication authentication) throws WKSRecruiterException;
}
