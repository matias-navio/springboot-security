package com.matias.springboot.app.security.springbootsecurity.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matias.springboot.app.security.springbootsecurity.persistence.entity.Users;
import com.matias.springboot.app.security.springbootsecurity.persistence.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // busca el user en DB por el username
        Users user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " no está en la base de datos!"));

        // Spring security maneja los permisos con esta interface SimpleGrantedAuthority
        /* 
         * obtenemos los roles del user
         * con un map los convertimos a SimpleGrantedAuthority con el prefijo ROLE_
         * lo convertimos en lista
         */
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role ->  new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
                .collect(Collectors.toList());

        // List<SimpleGrantedAuthority> authorities2 = new ArrayList<>();
        // user.getRoles()
        //     .forEach(role -> authorities2.add(new SimpleGrantedAuthority("ROLE_ " + role.getRoleEnum().name())));

        /*
         * hacemos algo parecido a lo de arriba
         * obtenemos los permisos a partir del role que obtenemos del user
        */
        user.getRoles().stream()
                        .flatMap(role -> role.getPermissionList().stream())
                        .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        
        
        /*
         * aca retornamos el user pero en un formato 
         * que entiende Spring Security, ya que ese User
         * pertenece a Spring Security
         */
        return new User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            user.isAccountNoExpired(), 
            user.isAccountNoLocked(),
            user.isCredentialNoExpired(),
            authorities
        );
    }

}
