package com.uniovi.sdi2223505spring.services;

import com.uniovi.sdi2223505spring.entities.User;
import com.uniovi.sdi2223505spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        User user = usersRepository.findByDni(dni); // Obtengo el usuario de la aplicación
        if (user == null)
            throw new UsernameNotFoundException(dni);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // Cargo el rol asociado al usuario
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getDni(), user.getPassword(), grantedAuthorities);
    }

}
