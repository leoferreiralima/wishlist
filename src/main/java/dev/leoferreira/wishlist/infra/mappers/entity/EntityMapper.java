package dev.leoferreira.wishlist.infra.mappers.entity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface EntityMapper <E, D>{
    E toEntity(D domainObj);

    D toDomain(E entityObj);

    default List<D> toDomainList(List<E> entityList) {
        return Optional.ofNullable(entityList)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDomain)
                .toList();
    }
}
