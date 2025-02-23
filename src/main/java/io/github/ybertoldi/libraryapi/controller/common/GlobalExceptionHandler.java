package io.github.ybertoldi.libraryapi.controller.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.ybertoldi.libraryapi.controller.dto.ErroCampo;
import io.github.ybertoldi.libraryapi.controller.dto.ErroResposta;
import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> list = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de Validacao",
                list);
    }

    @ExceptionHandler(AutorInexistenteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleAutorInexistenteException(AutorInexistenteException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleHttpMessageNotReadableException(InvalidFormatException e){
        String tipoDado = Arrays.asList(e.getTargetType().getName().split("[.]")).getLast();
        String campo = e.getPath().getFirst().getFieldName();
        ErroCampo erroCampo = new ErroCampo(campo, "dados incorretos. O conteudo deve ser do tipo " + tipoDado);

        return new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Incapaz de ler a requisicao HTTP",
                List.of(erroCampo)
        );
    }
}
