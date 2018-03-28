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
        boolean rolesFlag = true;
        for (String role : roles) {
            if (!RolesEnum.getEnums().contains(role)) {
                rolesFlag = false;
                break;
            }
        }
        return rolesFlag;
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
            throw new WKSRecruiterException(new WKSRecruiterException.Error("LOGIN_NOT_UNIQUE",
                    "Account with such login already exists. Try another one."));
        }
    }

    @Override
    public Account editRoles(String login, Collection<String> roles) throws WKSRecruiterException {
        Optional<Account> account = accountsRepository.findByLogin(login);
        if (account.isPresent()) {
            if (!checkIfRolesInEnum(roles)) {
                throw new WKSRecruiterException(new WKSRecruiterException.Error("ROLE_ERROR", "Wrong role name!"));
            }
            account.get().setRoles(roles);
            return accountsRepository.save(account.get());
        } else {
            throw new WKSRecruiterException(new WKSRecruiterException.Error("ACCOUNT_NOT_FOUND", "Account with such login does not exist."));
        }
    }
}
