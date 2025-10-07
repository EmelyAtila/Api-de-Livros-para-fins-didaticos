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
public class LivroController implements GenericController{

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try{
            // Acrescengtar Lógica para mapear dto para entidade
            Livro livro = mapper.toEntity(dto);
            // enviar a entidade para o service salvar na base de dados
            service.salvar(livro);
            // criar url para acesso dos dados do livro
            //retornar código created com header location
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
            // Vamos mapear com uma bliblioteca diferente do AutorDTO MapsTruck
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
