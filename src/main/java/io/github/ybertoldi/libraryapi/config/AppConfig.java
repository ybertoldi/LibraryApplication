package io.github.ybertoldi.libraryapi.config;

import io.github.ybertoldi.libraryapi.controller.mappers.AutorMapper;
import io.github.ybertoldi.libraryapi.controller.mappers.LivroMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AutorMapper autorMapper(){
        return AutorMapper.INSTANCE;
    }

    @Bean
    public LivroMapper livroMapper(){
        return LivroMapper.INSTANCE;
    }
}
