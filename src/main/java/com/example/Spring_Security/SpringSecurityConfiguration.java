package com.example.Spring_Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration  //this is configuration class for customising authentication using inmemory security modifying default spring security
public class SpringSecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){ //InMemoryUserDetailsManager is used when we want to store credentials in the code   itself
        UserDetails user= User.builder()
                .username("user")
                .password("password")
                .authorities("USER")
                .build();
        UserDetails admin=User.builder()
                .username("admin")
                .password("admin")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean   //we create this as we dont want to encrypt credentials, so no passwordEncoder is required
    public PasswordEncoder getEncoder(){    //PasswordEncoder is an interface which has many implementation classes ,it basically encrypts the credentials.In my case here, i am using BCryptPasswordEncoder , an implementing class to see the encrypted value of credentials, but am actually returning NoOpPasswordEncoder since i dont want to store encrypted value and i want to use unencrypted credentials
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("The encoded user is : "+ bCryptPasswordEncoder.encode("user"));
        System.out.println("The encoded password is : "+ bCryptPasswordEncoder.encode("password"));

        return NoOpPasswordEncoder.getInstance();
    }

}

