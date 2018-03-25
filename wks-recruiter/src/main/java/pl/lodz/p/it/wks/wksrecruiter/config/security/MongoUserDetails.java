package pl.lodz.p.it.wks.wksrecruiter.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;

import java.util.Collection;
import java.util.List;

public class MongoUserDetails implements UserDetails {

    private String login;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;

    public MongoUserDetails(Account account) {
        this.login = account.getLogin();
        this.password = account.getPassword();
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(account.getRoles().toArray(new String[account.getRoles().size()]));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
