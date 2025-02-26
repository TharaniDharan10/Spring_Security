package com.example.Spring_Security.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String username;

    String password;

    String authorities; //ADMIN,USER,TEST

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Arrays.stream(authorities.split(",")).map(authority-> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());    //this line takes the String of authorities, splits them, converts each splitted string into GrantedAuthority by mapping using its implementation class SimpleGrantedAuthority, and finally collected them as list

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
