package com.emelyatila.libraryapi.controller;

import com.emelyatila.libraryapi.controller.dto.CadastroLivroDTO;
import com.emelyatila.libraryapi.controller.dto.ErroResposta;
import com.emelyatila.libraryapi.controller.mappers.LivroMapper;
import com.emelyatila.libraryapi.exceptions.RegistroDuplicadoException;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try{
            Livro livro = mapper.toEntity(dto);
            // Acrescengtar Lógica para mapear dto para entidade
            // enviar a entidade para o service salvar na base de dados
            // criar url para acesso dos dados do livro
            //retornar código created com header location
            // Vamos mapear com uma bliblioteca diferente do AutorDTO MapsTruck
            return ResponseEntity.ok(dto);
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
