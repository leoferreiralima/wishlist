package dev.leoferreira.wishlist.infra.mappers.dtos;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ResponseDTOMapper<DTO, DOMAIN>{
    DTO toDTO(DOMAIN domainObj);

    default List<DTO> toDTOList(List<DOMAIN> domainList) {
        return Optional.ofNullable(domainList)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
