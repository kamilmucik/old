package pl.estrix.app.backend.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.estrix.app.backend.user.executor.ReadUserCommandExecutor;
import pl.estrix.app.backend.user.model.Role;
import pl.estrix.app.common.dto.model.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserLoginService implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private ReadUserCommandExecutor readUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = readUser.getByEmail(UserDto.builder().email(email).build());
//        System.out.println("user: " + user );
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }

        List<Role> roles = new ArrayList<>();
//        roles.add(user.getRole());
        roles.add(Role.valueOf(user.getRole()));
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        boolean enabled = user.isEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = !user.isLocked();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}