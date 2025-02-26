package com.example.Spring_Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration  //this is configuration class for customising authentication using inmemory security modifying default spring security
public class SpringSecurityConfiguration {

    //InMemoryUserDetailsManager and PasswordEncoder are used for authentication
    //SecurityFilterChain is used for authorisation
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){ //InMemoryUserDetailsManager is used when we want to store credentials in the code itself. Its an implementation of UserDetailsService interface
//        UserDetails user= User.builder()
//                .username("user")
////                .password("$2a$10$YL7AVBwfPbFrfvFvnwpWp.4OUmqLBM.QIzbJ4sfKsiEVVR38sRsva")   //use this if want to use inmemory security using encryption of password
//                .password("password") //use this if want to use inmemory security using no encryption of password
//                .authorities("USER")
//                .build();
//        UserDetails admin=User.builder()
//                .username("admin")
//                .password("admin")
//                .authorities("ADMIN")   //there can be list of authorities added
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean   //we create this as we dont want to encrypt credentials, so no passwordEncoder is required
    public PasswordEncoder getEncoder(){    //PasswordEncoder is an interface which has many implementation classes ,it basically encrypts the credentials.In my case here, i am using BCryptPasswordEncoder , an implementing class to see the encrypted value of credentials, but am actually returning NoOpPasswordEncoder since i dont want to store encrypted value and i want to use unencrypted credentials
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        System.out.println("The encoded user is : "+ bCryptPasswordEncoder.encode("user"));   //dont encrypt username
        System.out.println("The encoded password is : "+ bCryptPasswordEncoder.encode("password"));
//        return bCryptPasswordEncoder;   //returning BCryptPasswordEncoder means, this uses inmemory Security with encryption of password

        return NoOpPasswordEncoder.getInstance();   //returning NoOpPasswordEncoder means ,this uses inmemory Security without encryption of password
    }

    @Bean       //SecurityFilterChain is used for authorisation as per Authority of UserDetails
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/home").permitAll()   //we can also put like "/home/**" which means a path followed by any    .  This /home api page could be viewed by any user even without credentials, just like when we do amazon.com even without been signed in.
                        .requestMatchers("/admin").hasAuthority("ADMIN")    //these means all these apis can be accessed only by allowed authorities
                        .requestMatchers("/user").hasAuthority("USER")
                        .requestMatchers("/Spring_Security").hasAnyAuthority("ADMIN","USER")
                        .anyRequest().permitAll()) //we literally permitted all other methods as permitAll.We didnot bother about POSTAPI authentications also,bcoz we need CSRF token,else it wont open that POST itself
                .formLogin(withDefaults())  //this ensures that it can be opened in browser itself
                .httpBasic(withDefaults()) //this is used when we develop with only as backend,cause in most of the cases ,frontend will be built separately and backend separately, so it shouldnot redirect to a login page as its not required in this case and could be done only with API clients like postman.So if someone hits localhost:8080/Spring_Security in postman, he will not be redirected to login instead throws 401 error unauthorised
        .csrf(csrf-> csrf.disable());   //this is done only for testing purpose.This should be removed when our testing is done.Else other than GET methods ,for all methods it shows 403 error.This was disabled by us for testing API's which modifies resources,like POST,PUT,DELETE,PATCH API ,since these API's need csrf token for working and e cannot provide csrf token in postman each time
        return http.build();
    }



//    @Bean   //this bean was created to show JDBC security. Its an implementation of UserDetailsService interface
//    UserDetailsManager users(DataSource dataSource) {   //datasource bean is generated in app.properties
//        //only for DEMO(need not use them as codes here instead add values directly into table using queries)
//        //admin authorities here have both admin and user for demo
//        //to add new users in future, u can simply add them using quiries into tables and need not add them as code here even when the server is running
//        UserDetails user = User.builder()
//                .username("user")
//                .password("password")
//                .authorities("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("admin")
//                .authorities("USER", "ADMIN")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        if (!users.userExists(user.getUsername())) {
//            users.createUser(user);
//        }
//        if (!users.userExists(admin.getUsername())) {
//            users.createUser(admin);
//        }
//        return users;
//    }


//    @Bean   //this was created to show , when there are more than one implementation for UserDetailsService in code, to specify which implementation is to be considered
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider=new
//                DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());   //copy pasted from pdf and modified customUserDetailsService with userDetailsService() method of InMemoryUserDetailsManager
//
//        authenticationProvider.setPasswordEncoder(getEncoder());
//        return authenticationProvider;
//    }
}

