package pl.lodz.p.it.wks.wksrecruiter.services;

import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.collections.RolesEnum;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AccountServiceImpl implements AccountService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AccountsRepository accountsRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountsRepository = accountsRepository;
    }

    private boolean checkIfRolesInEnum(Collection<String> roles) {
        AtomicBoolean rolesFlag = new AtomicBoolean(true);
        roles.forEach(role -> {
            if (!RolesEnum.getEnums().contains(role)) {
                rolesFlag.set(false);
            }
        });
        return rolesFlag.get();
    }

    @Override
    public Account createAccount(Account account) throws WKSRecruiterException {
        WKSRecruiterException ex = new WKSRecruiterException();
        try {
            if (!checkIfRolesInEnum(account.getRoles())) {
                ex.add(new WKSRecruiterException.Error("ROLE_ERROR", "Wrong role name!"));
            }
            if (account.getPassword() == null || account.getPassword().equals("")) {
                ex.add(new WKSRecruiterException.Error("PASSWORD_EMPTY", "Password can not be empty!"));
            }
            if (!account.getPassword().matches("^.\\S*")) {
                ex.add(new WKSRecruiterException.Error("PASSWORD_WHITE", "Password cannot contain whitespaces!"));
            }
            if (account.getPassword().length() < 8 || account.getPassword().length() > 16) {
                ex.add(new WKSRecruiterException.Error("PASSWORD_LENGTH", "Password have to contain between 8 and 16 characters!"));
            }
            if (!ex.getErrors().isEmpty()) {
                throw ex;
            }
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            account.setSolvedTests(new ArrayList<>());
            accountsRepository.save(account);
            account.setPassword(null);
            return account;
        } catch (DuplicateKeyException exc) {
            throw WKSRecruiterException.createException("LOGIN_NOT_UNIQUE",
                    "Account with such login already exists. Try another one.");
        }
    }

    @Override
    public Account editRoles(String login, Collection<String> roles) throws WKSRecruiterException {
        Optional<Account> account = accountsRepository.findByLogin(login);
        if (account.isPresent()) {
            if (!checkIfRolesInEnum(roles)) {
                throw WKSRecruiterException.createException("ROLE_ERROR", "Wrong role name!");
            }
            account.get().setRoles(roles);
            accountsRepository.save(account.get());
            account.get().setPassword(null);
            return account.get();
        } else {
            throw WKSRecruiterException.createAccountNotFoundException();
        }
    }
}
