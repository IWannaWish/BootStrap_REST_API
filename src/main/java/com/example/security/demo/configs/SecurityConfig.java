package com.example.security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userService;
    private final SuccessUserHandler userHandler;


    @Autowired
    public SecurityConfig(UserDetailsService userService, SuccessUserHandler userHandler) {
        this.userHandler = userHandler;
        this.userService = userService;
    }

    //Security config
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(userHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf().disable();
//        http.authorizeHttpRequests()
//                .antMatchers("/admin").authenticated()//all page for authenticated user
////                .antMatchers("/admin/**").hasAnyRole("ADMIN") // pages for admin
//                .and()
//                .formLogin()
//                .and()
//                .logout().logoutSuccessUrl("/");
    }

    //In Memory
//    @Bean
//    public UserDetailsService users(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$14GUCyO2/EPo4s3n1DknF.MW9hnpQaCG00CRjsOUEx90iCjnndWQ.")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$14GUCyO2/EPo4s3n1DknF.MW9hnpQaCG00CRjsOUEx90iCjnndWQ.")
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    //    JDBC Authentication
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$14GUCyO2/EPo4s3n1DknF.MW9hnpQaCG00CRjsOUEx90iCjnndWQ.")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$14GUCyO2/EPo4s3n1DknF.MW9hnpQaCG00CRjsOUEx90iCjnndWQ.")
//                .roles("USER", "ADMIN")
//                .build();
//
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        if (users.userExists(user.getUsername())) {
//            users.deleteUser(user.getUsername());
//        }
//        if (users.userExists(admin.getUsername())) {
//            users.deleteUser(admin.getUsername());
//        }
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }


    //    DAO authentication provider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder()); //add encoder
        authenticationProvider.setUserDetailsService(userService); //add DetailService  (Users)
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
