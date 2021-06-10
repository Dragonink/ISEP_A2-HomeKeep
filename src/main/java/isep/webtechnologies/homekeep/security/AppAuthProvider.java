package isep.webtechnologies.homekeep.security;

import isep.webtechnologies.homekeep.models.user.User;
import isep.webtechnologies.homekeep.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AppAuthProvider extends DaoAuthenticationProvider {
    @Autowired
    UserService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String email = auth.getName();
        String providedPassword = auth.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(email);
        if (!User.encryptPassword(providedPassword).equals(user.getPassword())) {
            throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }
        return new UsernamePasswordAuthenticationToken(user, providedPassword, user.getAuthorities());
    }
    public AppAuthProvider(UserService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }
}
