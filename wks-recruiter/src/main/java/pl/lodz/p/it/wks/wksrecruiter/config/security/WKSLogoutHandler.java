package pl.lodz.p.it.wks.wksrecruiter.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.lodz.p.it.wks.wksrecruiter.collections.InvalidToken;
import pl.lodz.p.it.wks.wksrecruiter.repositories.InvalidTokensRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pl.lodz.p.it.wks.wksrecruiter.config.security.SecurityConstants.AUTHORIZATION_HEADER_NAME;
import static pl.lodz.p.it.wks.wksrecruiter.config.security.SecurityConstants.TOKEN_PREFIX;

public class WKSLogoutHandler implements LogoutHandler {

    private final InvalidTokensRepository invalidTokensRepository;

    public WKSLogoutHandler(InvalidTokensRepository invalidTokensRepository) {
        this.invalidTokensRepository = invalidTokensRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authToken = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authToken != null) {
            authToken = authToken.replace(TOKEN_PREFIX, "");
            invalidTokensRepository.save(new InvalidToken(authToken));
        }
    }
}
