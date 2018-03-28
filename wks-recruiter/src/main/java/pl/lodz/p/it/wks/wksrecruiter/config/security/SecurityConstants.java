package pl.lodz.p.it.wks.wksrecruiter.config.security;

public class SecurityConstants {

    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Token ";
    static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    static final String ROLES_CLAIM_NAME = "roles";

    static final String LOGIN_URL = "/login";
    static final String REGISTER_URL = "/register";

}
