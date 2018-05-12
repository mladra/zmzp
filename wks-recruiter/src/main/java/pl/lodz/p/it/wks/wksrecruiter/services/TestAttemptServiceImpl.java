package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.collections.AttemptAnswer;
import pl.lodz.p.it.wks.wksrecruiter.collections.RolesEnum;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Map<String, Collection<TestAttempt>> getAllTestsAttempts(Authentication authentication) throws WKSRecruiterException {
        Optional<Account> editor = this.accountsRepository.findByLogin(authentication.getName());
        if (editor.isPresent()) {
            if (editor.get().getRoles().contains(RolesEnum.EDITOR.toString())) {
                Map<String, Collection<TestAttempt>> testAttempts = new HashMap<>();
                List<Account> accounts = this.accountsRepository.findAll();

                accounts.forEach(acc -> {
                    List<TestAttempt> attempts = acc.getSolvedTests().stream()
                            .filter(t -> t.getTest().getAuthor().getName().equals(editor.get().getName()))
                            .collect(Collectors.toList());

                    if (attempts.size() > 0) {
                        testAttempts.put(acc.getLogin(), attempts);
                    }
                });

                return testAttempts;
            } else {
                throw WKSRecruiterException.createAcessDeniedException();
            }
        } else {
            throw WKSRecruiterException.createAccountNotFoundException();
        }
    }

    @Override
    public void evaluateTestAttempt(TestAttempt testAttempt, Authentication authentication) throws WKSRecruiterException {
        Optional<Account> editor = accountsRepository.findByLogin(authentication.getName());
        if (editor.isPresent()) {
            if (!editor.get().getRoles().contains(RolesEnum.EDITOR.toString())) {
                throw WKSRecruiterException.createAcessDeniedException();
            }
        } else {
            throw WKSRecruiterException.createAccountNotFoundException();
        }

        Optional<Account> accountOptional = accountsRepository.findAll().stream()
                .filter(acc -> acc.getSolvedTests()
                        .stream()
                        .anyMatch(t -> t.getTest().getId().equals(testAttempt.getTest().getId())))
                .findFirst();

        if (accountOptional.isPresent()) {
            TestAttempt attempt = accountOptional.get().getSolvedTests().stream()
                    .filter(t -> t.getTest().getId().equals(testAttempt.getTest().getId())).findFirst().get();

            if (!attempt.getTest().getAuthor().getName().equals(editor.get().getName())) {
                throw WKSRecruiterException.createAcessDeniedException();
            }

            attempt.setScore(0);
            for (AttemptAnswer attemptAnswer : attempt.getAnswers()) {
                Optional<AttemptAnswer> evaluatedAnswer = testAttempt.getAnswers().stream()
                        .filter(ans -> ans.getQuestionNumber() == attemptAnswer.getQuestionNumber()).findFirst();
                evaluatedAnswer.ifPresent(ans -> {
                    attemptAnswer.setScore(ans.getScore());
                    attempt.setScore(attempt.getScore() + ans.getScore());
                });
            }
            accountsRepository.save(accountOptional.get());
        } else {
            throw WKSRecruiterException.createTestAttemptNotFoundException();
        }
    }
}