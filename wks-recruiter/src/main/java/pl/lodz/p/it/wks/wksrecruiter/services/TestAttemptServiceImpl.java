package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

import java.util.Optional;

@Service
public class TestAttemptServiceImpl implements TestAttemptService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public TestAttemptServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Iterable<TestAttempt> getTestAttempts(Authentication authentication) throws WKSRecruiterException {
        Optional<Account> candidate = this.accountsRepository.findByLogin(authentication.getName());
        if (candidate.isPresent()) {
            return candidate.get().getSolvedTests();
        } else throw WKSRecruiterException.createAccountNotFoundException();
    }
}