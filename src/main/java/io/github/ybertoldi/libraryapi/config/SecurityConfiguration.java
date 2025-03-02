package io.github.ybertoldi.libraryapi.config;

import io.github.ybertoldi.libraryapi.security.CustumUserDetailsService;
import io.github.ybertoldi.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config -> config.loginPage("/login").permitAll())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(aut -> {
                    aut.requestMatchers("/login").permitAll();
                    aut.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    aut.requestMatchers("/autores/**").hasRole("ADMIN");
                    aut.requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USER","ADMIN");
                    aut.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
                    aut.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){
//        UserDetails u1 = User.builder()
//                .username("admin")
//                .password(encoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails u2 = User.builder()
//                .username("joao")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();

        return new CustumUserDetailsService(usuarioService);
    }
}
