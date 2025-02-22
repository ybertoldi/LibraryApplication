package io.github.ybertoldi.libraryapi.validator;

import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import io.github.ybertoldi.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    public AutorValidator(AutorRepository repository, LivroRepository livroRepository) {
        this.autorRepository = repository;
        this.livroRepository = livroRepository;
    }

    public void validar(Autor autor){
        if (autorRepository.existsByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()) ){
            throw new RegistroDuplicadoException("Autor ja consta no banco.");
        }

    }
}
