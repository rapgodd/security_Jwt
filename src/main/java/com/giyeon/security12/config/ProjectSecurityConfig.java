package com.giyeon.security12.config;

import com.giyeon.security12.filter.CsrfCookieFilter;
import com.giyeon.security12.filter.EmailValidationFilter;
import com.giyeon.security12.filter.JwtTokenGeneratorFilter;

import com.giyeon.security12.filter.JwtTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

        http
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//Jsession id 를 만들지 말라는 의미이다. 왜? JWT 사용할거니까 더이상 필요없음.

             .securityContext((context) -> context
                .requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))//session을 컨텍스트에 자동으로 저장하고
                                                                                                            //요청때마다 세션을 생성
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {//cross origin문제 해결

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowCredentials(true);//인증정보 받을건지
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));//상대방 url
                        config.setAllowedHeaders(Collections.singletonList("*"));//어떤 졸휴의 http 헤더 받을건지 . *이면 다 받을 수 있다는거
                        config.setAllowedMethods(Collections.singletonList("*"));// get , post, patch 뭐 받을건지. *이면 다 받을 수 있다는거
                        config.setExposedHeaders(Arrays.asList("Authorization"));// 우리가 JWT 토큰을 해더에 담아서 보낼때 그 해더 이름을 적음으로써 브라우저에서 볼 수 있게 해달라는 것이다.
                        config.setMaxAge(3600L);
                        return config;
                    }

                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))//JS에서 이거 읽을 수 있도록 함.
                .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenGeneratorFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailValidationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> {
                    requests
                            .requestMatchers("/user/information").hasRole("USER")
                            .requestMatchers("/").authenticated()
                            .requestMatchers("/welcome","/login", "/department", "/register", "/random").permitAll();
                });
        http.formLogin(Customizer.withDefaults());//폼 로그인 활성화
        http.httpBasic(Customizer.withDefaults());//HTTP 로그인 활성화
        return (SecurityFilterChain)http.build();
    }



    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService의 구현중 하나.
     */
//    @Bean
//    InMemoryUserDetailsManager userDetailsService() {
//        UserDetails admin = User.withUsername("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("12345")
//                .authorities("user")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


}
