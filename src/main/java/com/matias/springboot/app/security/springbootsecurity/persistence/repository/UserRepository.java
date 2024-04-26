package com.matias.springboot.app.security.springbootsecurity.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matias.springboot.app.security.springbootsecurity.persistence.entity.Users;
import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<Users, Long>{

    public Optional<Users> findByUsername(String username);
}
