package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //for h2-console
        http.headers().frameOptions().disable();

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/example/enableNewFeatures/**").hasRole("ADMIN")
                .antMatchers("/example/disableNewFeatures/**").hasRole("ADMIN")
                .antMatchers("/example/about").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/code/**").permitAll()
                .antMatchers("/h2-console/").permitAll()
                .and()
                .formLogin();
                //.loginPage("/login");

        return http.build();

    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        /*UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();

        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("user1")
                .roles("USER")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("user2")
                .roles("USER")
                .build();*/



       // return new InMemoryUserDetailsManager(List.of(admin, user1,user2));
        List<UserDetails> listUsers = userService.getAllUsers().stream().map(x->x.castUserToUserDetails()).collect(Collectors.toList());
        return new InMemoryUserDetailsManager(listUsers);
    }


}
