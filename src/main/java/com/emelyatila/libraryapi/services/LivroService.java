package com.emelyatila.libraryapi.services;

import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){ repository.delete(livro);}

    public List<Livro> pesquisa(
            String isbn, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        Specification<Livro> specs = null;
        return  repository.findAll(specs);
    }

}
