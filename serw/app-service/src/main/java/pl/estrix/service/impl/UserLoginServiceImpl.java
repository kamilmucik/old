package pl.estrix.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.estrix.commons.mock.ReplaceableComponent;
import pl.estrix.commons.Role;
import pl.estrix.domain.user.model.UserDto;
import pl.estrix.service.UserLoginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Primary
@ReplaceableComponent
public class UserLoginServiceImpl implements UserLoginService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = null;
//        UserDto.builder()
//                .email("k.mucik1@estrix.pl")
//                .password("f87918798b72fbb80b01cc38bdcedcaa82e9e6d171fb708ba816f5e626590fa67cd5bcd30cd642c6")
//                .build();
        if (user == null)
            throw new UsernameNotFoundException("User " + email + " not found");

        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
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
