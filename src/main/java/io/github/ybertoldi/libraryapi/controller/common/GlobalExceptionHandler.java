package io.github.ybertoldi.libraryapi.controller.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.ybertoldi.libraryapi.controller.dto.ErroCampo;
import io.github.ybertoldi.libraryapi.controller.dto.ErroResposta;
import io.github.ybertoldi.libraryapi.exceptions.AutorInexistenteException;
import io.github.ybertoldi.libraryapi.exceptions.OperacaoNaoPermitiaException;
import io.github.ybertoldi.libraryapi.exceptions.CampoInvalidoException;
import io.github.ybertoldi.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleAutorInexistenteException(AutorInexistenteException e){
        return ErroResposta.notFound(e.getMessage());
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitiaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitiaException e){
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

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        ErroCampo erroCampo = new ErroCampo(e.getCampo(), e.getMensagem());
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of(erroCampo));
    }
}
