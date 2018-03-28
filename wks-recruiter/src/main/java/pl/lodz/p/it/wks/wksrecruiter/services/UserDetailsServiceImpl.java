package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findAccountByLogin(username);

        if (account == null) {
            throw new UsernameNotFoundException("Username with provided login doesn't exist.");
        }

        return new User(
                account.getLogin(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRoles().toArray(new String[account.getRoles().size()]))
        );
    }
}
