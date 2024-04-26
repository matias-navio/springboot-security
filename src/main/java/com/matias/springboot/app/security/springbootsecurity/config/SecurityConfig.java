package com.matias.springboot.app.security.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.matias.springboot.app.security.springbootsecurity.services.UserDetailServiceImpl;

@Configuration // clase de configuracion
@EnableWebSecurity // habilita la seguridad web
@EnableMethodSecurity // permite configurar con anotaciones
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration configuration;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    // aca es donde incluimos todos los filtros que debe pasar el erequest de los métodos HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults()) // indica que se va a loguear solo con username y password, sin token
            .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authz) -> {
                // esa ruta tiene acceso publico, es decir, cualquier porsona puede entrar
                authz.requestMatchers(HttpMethod.GET, "/auth/get")
                    .permitAll();

                // estas rutas tienen acceso privado, tipo POST
                authz.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER", "USER"); 

                // estas rutas tienen acceso privado, tipo DELETE
                authz.requestMatchers(HttpMethod.DELETE, "/auth/delete").hasAnyRole("ADMIN", "DEVELOPER"); 

                // estas rutas tienen acceso privado, tipo PUT
                authz.requestMatchers(HttpMethod.PUT, "/auth/put").hasAnyRole("ADMIN", "DEVELOPER"); 

                // se configura los endpoints que no se especificaron, con authenticated se necesita auntenticacion
                // authz.anyRequest().authenticated();
                // con denyAll deniaga a todo el mundo, aunque esté autenticado
                authz.anyRequest().denyAll();
            })
            .build();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    //     return http
    //         .csrf(csrf -> csrf.disable())
    //         .httpBasic(Customizer.withDefaults()) // indica que se va a loguear solo con username y password, sin token
    //         .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .build();
    // }
    
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{

        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailServiceImpl);

        return provider;    
    }

    // encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
