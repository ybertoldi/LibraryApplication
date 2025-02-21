package io.github.ybertoldi.libraryapi;

import io.github.ybertoldi.libraryapi.model.Autor;
import io.github.ybertoldi.libraryapi.model.DTO.AutorDTO;
import io.github.ybertoldi.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> cadastroDeAutor(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.toAutor();
        service.salvaAutor(autorEntidade);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        Optional<Autor> autor = service.obterPorId(uuid);

        if (autor.isPresent()){
            Autor autor1 = autor.get();
            AutorDTO dto = new AutorDTO(
                    autor1.getId(),
                    autor1.getNome(),
                    autor1.getDataNascimento(),
                    autor1.getNacionalidade()
            );

            System.out.println(dto);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaAutor(@PathVariable("id") String id){
        UUID uuid = UUID.fromString(id);
        Optional<Autor> optionalAutor = service.obterPorId(uuid);

        if (optionalAutor.isPresent()){
            System.out.println("Autor deletado: " + optionalAutor.get().getNome());
            service.deletarPorId(optionalAutor.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){

        List<Autor> pesquisa = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> dtos = pesquisa
                .stream()
                .map(
              autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        dtos.forEach(System.out::println);
        return ResponseEntity.ok(dtos);
    }
}
