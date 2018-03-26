package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.AppException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AccountsRepository accountsRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Account register(Account account) throws AppException {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setSolvedTests(new ArrayList<>());
        return accountsRepository.save(account);
    }
}
