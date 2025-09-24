package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.AutorDTO;
import com.emelyatila.libraryapi.controller.dto.ErroResposta;
import com.emelyatila.libraryapi.controller.mappers.AutorMapper;
import com.emelyatila.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
//http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto){
        try {
            Autor autorEntidade = mapper.toEntity(dto);
            service.salvar(autorEntidade);

            // criar url com id
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/ {id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto =  new AutorDTO(autor.getId(),
                                         autor.getNome(),
                                         autor.getDataNascimento(),
                                         autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build(); 
    }
// opção para limpar quebra de linha na url, mas no postman usei ../:id para receber o id por parametro e não url
//    @GetMapping("{id}")
//    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
//        try {
//            // Remove espaços e quebras de linha
//            UUID idAutor = UUID.fromString(id.trim());
//
//            Optional<Autor> autorOptional = service.obterPorId(idAutor);
//
//            if (autorOptional.isPresent()) {
//                Autor autor = autorOptional.get();
//                AutorDTO dto = new AutorDTO(
//                        autor.getId(),
//                        autor.getNome(),
//                        autor.getDataNascimento(),
//                        autor.getNacionalidade()
//                );
//                return ResponseEntity.ok(dto);
//            }
//
//            return ResponseEntity.notFound().build();
//
//        } catch (IllegalArgumentException e) {
//            // ID inválido → retorna 400 em vez de 500
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        }catch(OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List <AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false)String nacionalidade){

            List<Autor> resultado = service.pesquisaByExemple(nome,nacionalidade);
            List<AutorDTO> lista = resultado
                    .stream()
                    .map(autor -> new AutorDTO(
                            autor.getId(),
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade())
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid AutorDTO dto){

        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
