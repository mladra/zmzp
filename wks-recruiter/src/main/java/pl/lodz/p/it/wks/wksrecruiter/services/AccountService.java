package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import java.util.Collection;

public interface AccountService {
    Account createAccount(Account account) throws WKSRecruiterException;

    Account editRoles(String login, Collection<String> roles) throws WKSRecruiterException;
}
