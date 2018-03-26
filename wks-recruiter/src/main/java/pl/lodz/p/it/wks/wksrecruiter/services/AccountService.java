package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.AppException;

public interface AccountService {
    Account register(Account account) throws AppException;
}
