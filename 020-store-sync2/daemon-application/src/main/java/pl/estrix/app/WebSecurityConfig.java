package pl.estrix.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.estrix.app.authentication.ExpiredSessionExceptionHandler;
import pl.estrix.app.authentication.ExpiredSessionExceptionHandlerFilter;
import pl.estrix.app.backend.user.service.UserLoginService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Bean(name = "standardPasswordEncoder")
    public PasswordEncoder standardPasswordEncoder() {
        return new StandardPasswordEncoder("supersecret");
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserLoginService();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(standardPasswordEncoder());
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        webSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return webSecurityExpressionHandler;
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

    @Bean
    public AffirmativeBased accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoterList = new ArrayList<>();
        decisionVoterList.add(roleHierarchyVoter());
        decisionVoterList.add(webExpressionVoter());
        return new AffirmativeBased(decisionVoterList);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/secured/view/**").fullyAuthenticated()
                .antMatchers("/secured/**").fullyAuthenticated()
                .antMatchers("/secured/*").fullyAuthenticated()
                .antMatchers("/secured/admin/**", "/secured/view/admin/**").access("hasRole('ROLE_SUPERUSER')")
                .antMatchers("/index.xhtml", "/index.html", "/app/login.xhtml", "/javax.faces.resources/**").permitAll()
                .antMatchers("/login*").anonymous()
                .and()
                .formLogin()
                    .loginPage("/index.html")
                    .loginProcessingUrl("/perform_login.html")
                    .failureUrl("/login.html?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/index.html")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .addFilterAfter(new ExpiredSessionExceptionHandlerFilter(expiredSessionExceptionHandler), ExceptionTranslationFilter.class)
                    .exceptionHandling()
                    .accessDeniedPage("/error.html")
                .and()
                    .csrf()
                    .disable();

    }

    @Autowired
    private ExpiredSessionExceptionHandler expiredSessionExceptionHandler;

    @Bean
    public ExpiredSessionExceptionHandler expiredSessionExceptionHandler() {
        return new ExpiredSessionExceptionHandler();
    }


    @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/favicon.ico";
        }
    }

}