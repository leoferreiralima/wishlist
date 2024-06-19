package dev.leoferreira.wishlist.infra.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        name = "userId",
        description = "User identifier",
        required = true,
        schema = @Schema(type = "string"),
        in = ParameterIn.PATH
)
public @interface ApiUserParam {
}
