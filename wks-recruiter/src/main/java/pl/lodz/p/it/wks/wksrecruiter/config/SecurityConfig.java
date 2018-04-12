package pl.lodz.p.it.wks.wksrecruiter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.lodz.p.it.wks.wksrecruiter.config.security.JWTAuthenticationFilter;
import pl.lodz.p.it.wks.wksrecruiter.config.security.JWTAuthorizationFilter;
import pl.lodz.p.it.wks.wksrecruiter.config.security.WKSLogoutHandler;
import pl.lodz.p.it.wks.wksrecruiter.repositories.AccountsRepository;
import pl.lodz.p.it.wks.wksrecruiter.repositories.InvalidTokensRepository;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static pl.lodz.p.it.wks.wksrecruiter.config.security.SecurityConstants.LOGIN_URL;
import static pl.lodz.p.it.wks.wksrecruiter.config.security.SecurityConstants.LOGOUT_URL;
import static pl.lodz.p.it.wks.wksrecruiter.config.security.SecurityConstants.REGISTER_URL;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AccountsRepository accountsRepository;
    private final InvalidTokensRepository invalidTokensRepository;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          AccountsRepository accountsRepository, InvalidTokensRepository invalidTokensRepository) {
        this.userDetailsService = userDetailsService;
        this.accountsRepository = accountsRepository;
        this.invalidTokensRepository = invalidTokensRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
                .antMatchers("/tests/**/xls").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), accountsRepository))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), invalidTokensRepository))
                .logout()
                    .addLogoutHandler(new WKSLogoutHandler(invalidTokensRepository))
                    .logoutUrl(LOGOUT_URL)
                    .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) ->
                            httpServletResponse.setStatus(HttpServletResponse.SC_OK))
                    .permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        configuration.setMaxAge(3600L);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
