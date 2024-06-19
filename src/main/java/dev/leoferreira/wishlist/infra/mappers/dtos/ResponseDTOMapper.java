package dev.leoferreira.wishlist.infra.mappers.dtos;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ResponseDTOMapper<T, D>{
    T toDTO(D domainObj);

    default List<T> toDTOList(List<D> domainList) {
        return Optional.ofNullable(domainList)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
