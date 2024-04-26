package com.matias.springboot.app.security.springbootsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
// @PreAuthorize("denyAll()") // esto deniega todo antes de entrar
public class TestAuthController {
    
    @GetMapping("/get")
    // @PreAuthorize("hasAuthority('READ')")
    public String helloGet(){
        return "Hello Word - GET";
    }

    @PostMapping("/post")
    // @PreAuthorize("hasAuthority('CREATE') and hasAuthority('READ') and hasAuthority('REFACTOR')")
    public String helloPost(){
        return "Hello Word - post";
    }

    @PutMapping("/put")
    // @PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPut(){
        return "Hello Word - put";
    }

    @DeleteMapping("/delete")
    // @PreAuthorize("hasAuthority('CREATE') or hasAuthority('UPDATE')")
    public String helloDelete(){
        return "Hello Word - delete";
    }
}
