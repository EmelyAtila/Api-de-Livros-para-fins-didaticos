package com.emelyatila.libraryapi.controller.mappers;

import com.emelyatila.libraryapi.controller.dto.CadastroLivroDTO;
import com.emelyatila.libraryapi.model.Livro;
import com.emelyatila.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

@Autowired
AutorRepository autorRepository;

@Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
public abstract Livro toEntity(CadastroLivroDTO dto);
}
