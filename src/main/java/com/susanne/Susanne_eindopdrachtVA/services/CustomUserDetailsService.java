package com.susanne.Susanne_eindopdrachtVA.services;
import com.susanne.Susanne_eindopdrachtVA.model.Authority;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserService userService;


    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        Set<Authority> authorities = user.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }
}

