package com.domein.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.domein.jwt.JWTFilter;
import com.domein.jwt.JWTUtil;
import com.domein.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

	//AuthenticationManager Bean 등록
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
	 }
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//csrf disable
        http
                .csrf((auth) -> auth.disable());
        

		//From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

		//http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

		//경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join", "/api/login", "/api/join", 
                        				 "/api/check_username", "/api/search", "/character" ).permitAll()
												.requestMatchers("/admin").hasRole("ADMIN")
												.requestMatchers("/mypage", "/market", "/community").hasRole("USER")
                        .anyRequest().authenticated());
        http
        		.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
        .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));
            return configuration;
        }));
		//세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
