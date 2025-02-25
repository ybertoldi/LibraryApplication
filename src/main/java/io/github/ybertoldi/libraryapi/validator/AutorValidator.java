package io.github.ybertoldi.libraryapi.validator;

import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository repository) {
        this.autorRepository = repository;
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
