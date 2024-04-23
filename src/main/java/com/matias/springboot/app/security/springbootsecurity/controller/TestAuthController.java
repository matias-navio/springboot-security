package com.matias.springboot.app.security.springbootsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthController {
    
    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public String hello(){
        return "Hola mundo";
    }

    @GetMapping("/hello-secured")
    @PreAuthorize("hasAnyAuthority('READ')")
    public String helloSecured(){
        return "Hola mundo secured";
    }

    @GetMapping("/hello-secured2")
    @PreAuthorize("hasAnyAuthority('CREATE')")
    public String helloSecured2(){
        return "Hola mundo secured2";
    }
}
