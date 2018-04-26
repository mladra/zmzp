package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;
import java.util.List;

public interface AccountService {
    Account createAccount(Account account) throws WKSRecruiterException;

    Account editRoles(String login, Collection<String> roles) throws WKSRecruiterException;

    Account deleteAccount(String login) throws WKSRecruiterException;

    Account editAccount(Account account) throws WKSRecruiterException;

    List<Account> getAll();

    Account register(Account account) throws WKSRecruiterException;

    Account addSolveTest(String login, TestAttempt testAttempt) throws WKSRecruiterException;
}
