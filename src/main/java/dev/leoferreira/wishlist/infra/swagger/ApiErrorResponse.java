package dev.leoferreira.wishlist.infra.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schemaProperties = {
                                        @SchemaProperty(
                                                name = "timestamp",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "When error occurred"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "status",
                                                schema = @Schema(
                                                        type = "number",
                                                        description = "Error Http status"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "error",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error Http status description"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "message",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error message"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "path",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error Api Path"
                                                )
                                        )
                                }
                        )
                }
        ),
        @ApiResponse(
                responseCode = "404",
                content = {
                        @Content(
                                mediaType = "application/json",
                                schemaProperties = {
                                        @SchemaProperty(
                                                name = "timestamp",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "When error occurred"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "status",
                                                schema = @Schema(
                                                        type = "number",
                                                        description = "Error Http status"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "error",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error Http status description"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "message",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error message"
                                                )
                                        ),
                                        @SchemaProperty(
                                                name = "path",
                                                schema = @Schema(
                                                        type = "string",
                                                        description = "Error Api Path"
                                                )
                                        )
                                }
                        )
                }
        )
})
public @interface ApiErrorResponse {
}
