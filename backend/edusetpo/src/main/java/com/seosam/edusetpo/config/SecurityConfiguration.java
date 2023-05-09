package com.seosam.edusetpo.config;

import com.seosam.edusetpo.config.filter.JwtAuthenticationFilter;
import com.seosam.edusetpo.config.handler.CustomAuthenticationProvider;
import com.seosam.edusetpo.config.handler.JwtTokenProvider;
import com.seosam.edusetpo.tutor.service.TutorService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;


// 이 configuration을 설정안해주면 모든 페이지에서 로그인 하라고 막아버려서 signup조차 못함

//@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder를 Bean 등록
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // authenticationManager를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/api/tutor/login",
                        "/api/tutor/signup",
                        "/api/tutor/nickname",
                        "/api/tutor/email",
                        "/api/parent/login",
                        "/api/parent/signup").permitAll()
                .antMatchers("/api/tutor/**", "/api/parent/**").hasRole("USER")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
//    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomAuthenticationProvider authProvider;
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfiguration(CustomAuthenticationProvider authProvider, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.authProvider = authProvider;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    // 걸러내지 않는 페이지를 직접 설정
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll()
////                .antMatchers("/", "/tutor/signup", "/tutor/login", "/parent/login", "/parent/signup").permitAll()
//                .anyRequest().authenticated()
//                .and()
////                .formLogin()
////                .and()
//                .csrf().disable();
////                .anyRequest().authenticated();
////                .mvcMatchers("/", "/login", "/tutor/signup", "/check-email", "/check-email-token",
////                        "/swagger-ui/index.html", "/login-link", "/tutor/signuptest").permitAll()
////                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
////                .anyRequest().authenticated();
//
//        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//        http.formLogin().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//        auth.authenticationProvider(authProvider);
//    }
//
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
