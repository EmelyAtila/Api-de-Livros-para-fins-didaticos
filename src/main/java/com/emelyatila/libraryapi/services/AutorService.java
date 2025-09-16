package com.emelyatila.libraryapi.services;

import com.emelyatila.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.repository.AutorRepository;
import com.emelyatila.libraryapi.repository.LivroRepository;
import com.emelyatila.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;


    public Autor salvar (Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar (Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor já esteja cadastrado na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuilivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é possivél excluir Autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome,nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public boolean possuilivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
