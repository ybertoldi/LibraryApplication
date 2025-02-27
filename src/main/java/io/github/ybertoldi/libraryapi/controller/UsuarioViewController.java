package io.github.ybertoldi.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioViewController {

    @GetMapping("/pesquisar-livros")
    public String paginaPesquisaLivros(){
        return "pesquisar-livros";
    }
}
