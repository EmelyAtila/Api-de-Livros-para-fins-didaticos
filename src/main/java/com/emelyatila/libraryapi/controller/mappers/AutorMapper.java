package com.emelyatila.libraryapi.controller.mappers;

import com.emelyatila.libraryapi.controller.dto.AutorDTO;
import com.emelyatila.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    // metodo pra retornar autor (tranfroma autor em entidade)
    Autor toEntity(AutorDTO dto);


    // metodo pra retornar entidade (tranfroma entidade em autor)
    AutorDTO toDTO(Autor autor);
}
