package pl.estrix;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import pl.estrix.service.UserLoginService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private UserLoginService service;

    @Bean(name = "standardPasswordEncoder")
    public PasswordEncoder standardPasswordEncoder() {
        return new StandardPasswordEncoder("supersecret");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/javax.faces.resource/**")
                    .permitAll().anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/secured/helloworld",true)
                    .failureUrl("/login?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error")
                .and()
                    .csrf()
                    .disable();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return service;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("john.doe").password("{noop}1234").roles("USER")
//                .and()
//                .withUser("jane.doe").password("{noop}5678").roles("ADMIN");
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(standardPasswordEncoder());
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_SUPERUSER > ROLE_OPERATOR > ROLE_USER ");
        return roleHierarchy;
    }

}