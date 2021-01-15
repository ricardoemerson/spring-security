package com.example.springsecurity.security;


import com.example.springsecurity.auth.ApplicationUserService;
import com.example.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ApplicationUserService applicationUserService;

    // public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
    //     this.passwordEncoder = passwordEncoder;
    //     this.applicationUserService = applicationUserService;
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                // .antMatchers("/api/**").hasRole(STUDENT.name())
                // .antMatchers(HttpMethod.POST,
                // "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                // .antMatchers(HttpMethod.PUT,
                // "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                // .antMatchers(HttpMethod.DELETE,
                // "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                // .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(),
                // ADMINTRAINEE.name())
            .anyRequest()
            .authenticated();
            // .and()
            // .httpBasic();
    }

    // @Override
    // @Bean
    // protected UserDetailsService userDetailsService() {
    // UserDetails ricardo = User.builder()
    // .username("ricardo")
    // .password(passwordEncoder.encode("as1mn1"))
    // // .roles(ADMIN.name())
    // .authorities(ADMIN.getGrantedAuthorities())
    // .build();

    // UserDetails derek = User.builder()
    // .username("derek")
    // .password(passwordEncoder.encode("as1mn1"))
    // // .roles(ADMINTRAINEE.name())
    // .authorities(ADMINTRAINEE.getGrantedAuthorities())
    // .build();

    // UserDetails ted = User.builder()
    // .username("ted")
    // .password(passwordEncoder.encode("as1mn1"))
    // // .roles(STUDENT.name())
    // .authorities(STUDENT.getGrantedAuthorities())
    // .build();

    // return new InMemoryUserDetailsManager(ricardo, ted, derek);
    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }

}
