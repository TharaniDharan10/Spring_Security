package com.example.Spring_Security.service;

import com.example.Spring_Security.model.MyUser;
import com.example.Spring_Security.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  //Only for demo (hardcoded)
        //        return new User("abc","abc", Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))); //run time polymorphism //what it tells is, whatever be the username i enter during login, it only validates password but not username.We get bad credentials only when we enter wrong password from AuthenticationManager

//        return  myUserRepository.findByUsername(username);  //i cannot return it as spring security doesnot know what is MyUser.In that case we have to map that MyUser , i can do either of 2 things.

//        //way 1 : create MyUser here, map MyUser with UserDetails
//        MyUser myUser = myUserRepository.findByUsername(username);
//        List<GrantedAuthority> grantedAuthorities = Arrays.stream(myUser.getAuthorities().split(",")).map(authority->new SimpleGrantedAuthority(authority)).collect(Collectors.toList());  //this line takes the String of authorities, splits them, converts each splitted string into GrantedAuthority by mapping using its implementation class SimpleGrantedAuthority, and finally collected them as list
//        return new User(myUser.getUsername(),myUser.getPassword(),grantedAuthorities); //since we stored authorities as string,but since it can have multiple authorities,so we convert into list

        //way 2 : implement MyUser with UserDetails
        MyUser myUser = myUserRepository.findByUsername(username);
        if(myUser != null){
            return myUser;
        }
        //we throw this execption in case if user not found from db to avoid "UserDetailsService returned null, which is an interface contract violation" error message.Now after adding enxt line,i try to enter a credential which is not found in db, it shows bad credentials
        throw new UsernameNotFoundException(username.concat(" not found in repository"));
    }
}
