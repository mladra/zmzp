package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountsRepository.findByLogin(username);

        if (!account.isPresent()) {
            throw new UsernameNotFoundException("Username with provided login doesn't exist.");
        } else if (!account.get().getEnabled()) {
            throw new DisabledException("Your account is being deleted.");
        }

        return new User(
                account.get().getLogin(),
                account.get().getPassword(),
                AuthorityUtils.createAuthorityList(account.get().getRoles().toArray(new String[0]))
        );
    }
}
