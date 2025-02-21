package io.github.ybertoldi.libraryapi.repository;

import io.github.ybertoldi.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {
    @Autowired
    TransacaoService transacaoService;

    /**
     * commit -> implementar as alterações
     * rollBack -> descartar as auterações
     */
    @Test
    @Transactional
    void transicaoExTest(){
        // salvar livro
        // salvar livro
        // alugar livro
        // enviar e-mail
        // notificar que o livro saiu da livraria

        // as alterações só serão feitas se todos os passos derem certo
    }

    @Test
    void transacaoSimplesTest(){
        transacaoService.executar();
    }
}
