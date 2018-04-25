package se.viia.quest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import se.viia.quest.account.AccountService;
import se.viia.quest.auth.filter.JwtAuthenticationFilter;
import se.viia.quest.auth.provider.RefreshAuthProvider;
import se.viia.quest.auth.token.TokenHandler;
import se.viia.quest.util.SecurityUtils;

/**
 * @author affe 2018-04-25
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final TokenHandler tokenHandler;

    @Autowired
    public SecurityConfig(AccountService accountService, TokenHandler tokenHandler) {
        this.accountService = accountService;
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login", "/auth/refresh").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationFilter(new AntPathRequestMatcher("/api/**"), tokenHandler), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(new RefreshAuthProvider(tokenHandler, accountService));
//        http.exceptionHandling().authenticationEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(SecurityUtils.passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
