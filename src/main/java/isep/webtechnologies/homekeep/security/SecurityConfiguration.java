package isep.webtechnologies.homekeep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import isep.webtechnologies.homekeep.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authenticationProvider(new AppAuthProvider(userDetailsService))
                .formLogin()
                    .loginProcessingUrl("/login")
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/css/**", "/js/**", "/images/**", "/registration", "/api/houses/*", "/api/houseimages/image/*")
                        .permitAll()
                    .anyRequest()
                        .authenticated();
    }
    //TODO add constants for public/private pages ?
    // or sort them by package
}
