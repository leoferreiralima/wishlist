package dev.leoferreira.wishlist.infra.mappers.entity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface EntityMapper <ENTITY, DOMAIN>{
    ENTITY toEntity(DOMAIN domainObj);

    DOMAIN toDomain(ENTITY entityObj);

    default List<DOMAIN> toDomainList(List<ENTITY> entityList) {
        return Optional.ofNullable(entityList)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDomain)
                .toList();
    }
}
