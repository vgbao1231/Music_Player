package org.music_player.web.service.custom;

import org.music_player.web.entity.CustomUserDetails;
import org.music_player.web.entity.User;
import org.music_player.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        if (user==null){
            throw new UsernameNotFoundException("User does not exist!");
        }
        Collection <GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        String role = user.getRole();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(role));
        return new CustomUserDetails(user,grantedAuthoritySet);
    }
}
