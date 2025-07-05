package com.taskmanagement.core.security;


import com.sushikhacapitals.common.utils.AutowireUtils;
import com.taskmanagement.core.security.filters.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration {

    @Autowired
    private AutowireUtils autowireUtils;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .anyRequest().fullyAuthenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic((AbstractHttpConfigurer::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(logoutSuccessHandler))
                .addFilterBefore(autowireUtils.autowire(new JwtAuthenticationFilter()),UsernamePasswordAuthenticationFilter.class).build();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowCredentials(false)
                        .allowedMethods("GET","HEAD","PUT","POST","DELETE","OPTIONS");
            }
        };
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**");
//    }
}
