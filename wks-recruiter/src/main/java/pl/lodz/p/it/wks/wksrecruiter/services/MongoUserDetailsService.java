package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.config.security.MongoUserDetails;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;

@Primary
@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public MongoUserDetailsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findAccountByLogin(username);

        if (account == null) {
            throw new UsernameNotFoundException("Username with provided login doesn't exist.");
        }

        return new MongoUserDetails(account);
    }
}
