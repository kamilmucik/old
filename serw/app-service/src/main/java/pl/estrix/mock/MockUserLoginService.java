package pl.estrix.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.estrix.commons.mock.BaseMockService;
import pl.estrix.commons.mock.ReplacingMockComponent;
import pl.estrix.commons.Role;
import pl.estrix.domain.user.model.UserDto;
import pl.estrix.service.UserLoginService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@ReplacingMockComponent( clazz = pl.estrix.service.impl.UserLoginServiceImpl.class)
public class MockUserLoginService extends BaseMockService {

    @Bean(name = "mockUserLoginService")
    UserLoginService userLoginService(){
        return new UserLoginService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                UserDto user = UserDto.builder()
                        .email("k.mucik@estrix.pl")
                        .password("f87918798b72fbb80b01cc38bdcedcaa82e9e6d171fb708ba816f5e626590fa67cd5bcd30cd642c6")
                        .build();

                List<Role> roles = new ArrayList<>();
                roles.add(Role.ROLE_USER);
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for (Role role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role.toString()));
                }

                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
            }
        };
    }
}
