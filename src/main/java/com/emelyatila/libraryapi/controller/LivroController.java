package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.CadastroLivroDTO;
import com.emelyatila.libraryapi.controller.dto.ErroResposta;
import com.emelyatila.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.emelyatila.libraryapi.controller.mappers.LivroMapper;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // Acrescengtar Lógica para mapear dto para entidade
        Livro livro = mapper.toEntity(dto);
        // enviar a entidade para o service salvar na base de dados
        service.salvar(livro);
        // criar url para acesso dos dados do livro
        //retornar código created com header location
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
            @PathVariable("id") String id){

        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                })
    }
}
