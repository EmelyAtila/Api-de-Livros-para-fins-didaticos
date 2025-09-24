package com.emelyatila.libraryapi.controller.mappers;

import com.emelyatila.libraryapi.controller.dto.AutorDTO;
import com.emelyatila.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
