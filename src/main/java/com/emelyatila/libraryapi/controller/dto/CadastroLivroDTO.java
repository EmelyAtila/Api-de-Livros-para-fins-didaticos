package com.emelyatila.libraryapi.controller.dto;

import com.emelyatila.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo é obrigatório")
        String isbn,
        @NotBlank(message = "campo é obrigatório")
        String titulo,
        @NotNull (message = "campo é obrigatório")
        @Past(message = "Data inválida: Não pode ser uma data futura!")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo é obrigatório")
        UUID idAutor
) {

}
