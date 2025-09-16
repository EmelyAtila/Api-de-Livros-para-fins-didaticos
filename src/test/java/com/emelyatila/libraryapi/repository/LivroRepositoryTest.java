package com.emelyatila.libraryapi.repository;

import com.emelyatila.libraryapi.model.Autor;
import com.emelyatila.libraryapi.model.GeneroLivro;
import com.emelyatila.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("1212121-551");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Matematica");
        livro.setDataPublicacao(LocalDate.of(2024,3,25));

        Autor autor = autorRepository
                .findById(UUID.fromString("d1a71dcb-09f0-4dd4-bed9-723f28df94fe"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void salvarCascadeTeste(){
        Livro livro = new Livro();
        livro.setIsbn("1212121-551");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Matematica");
        livro.setDataPublicacao(LocalDate.of(2024,3,25));

        Autor autor = new Autor();
        autor.setNome("Emely");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        livro.setAutor(autor);

        repository.save(livro);


    }
}
