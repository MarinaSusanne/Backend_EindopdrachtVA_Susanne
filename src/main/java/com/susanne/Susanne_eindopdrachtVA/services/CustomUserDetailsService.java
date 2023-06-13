package com.susanne.Susanne_eindopdrachtVA.services;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Authority;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserService userService;
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        String password = user.getPassword();
        UserOutputDto userDto = userService.getUserByUsername(username);
//        String password = userDto.getPassword();
        //TODO: ik wilde eigenlijk niet dat password in DTO staat, kan ik dit als volgt aanpakken in mijn CustomUserDetail
        Set<Authority> authorities = userDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
    }
}

//TODO: vragen hoe ik met een ROLE moet omgaan...