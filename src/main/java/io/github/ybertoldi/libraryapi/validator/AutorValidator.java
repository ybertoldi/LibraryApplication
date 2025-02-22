package io.github.ybertoldi.libraryapi.validator;

import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (repository.existsByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()) ){
            throw new RegistroDuplicadoException("Autor ja consta no banco.");
        }
    }
}
