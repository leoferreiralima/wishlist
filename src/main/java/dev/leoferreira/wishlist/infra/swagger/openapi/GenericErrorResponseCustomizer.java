package dev.leoferreira.wishlist.infra.swagger.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.Collection;

public class GenericErrorResponseCustomizer implements OpenApiCustomizer {

    @SuppressWarnings("rawtypes")
    private final static Schema GENERIC_ERROR_RESPONSE_SCHEMA = new ObjectSchema()
            .addProperty("timestamp", new DateTimeSchema())
            .addProperty("status", new IntegerSchema())
            .addProperty("error", new StringSchema())
            .addProperty("message", new StringSchema())
            .addProperty("path", new StringSchema());

    private static final String APPLICATION_JSON = org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
    private static final String GENERIC_ERROR_RESPONSE_SCHEMA_REF = "#/components/schemas/GenericErrorResponse";

    private static final ApiResponse BAD_REQUEST_RESPONSE = new ApiResponse()
            .description("Bad Request")
            .content(
                    new Content().addMediaType(
                            APPLICATION_JSON,
                            new MediaType().schema(
                                    new Schema<>().$ref(GENERIC_ERROR_RESPONSE_SCHEMA_REF)
                            )
                    )
            );

    private final static ApiResponse NOT_FOUND_RESPONSE = new ApiResponse()
            .description("Not Found")
            .content(
                    new Content().addMediaType(
                            APPLICATION_JSON,
                            new MediaType().schema(
                                    new Schema<>().$ref(GENERIC_ERROR_RESPONSE_SCHEMA_REF)
                            )
                    )
            );

    private final static ApiResponse SERVER_ERROR_RESPONSE = new ApiResponse()
            .description("Internal Server Error")
            .content(
                    new Content().addMediaType(
                            APPLICATION_JSON,
                            new MediaType().schema(
                                    new Schema<>().$ref(GENERIC_ERROR_RESPONSE_SCHEMA_REF)
                            )
                    )
            );

    @Override
    public void customise(OpenAPI openApi) {
        addGenericErrorResponseSchema(openApi);

        addGenericErrorResponseToOperations(openApi);
    }

    private void addGenericErrorResponseToOperations(OpenAPI openApi) {
        openApi.getPaths()
                .values()
                .stream()
                .map(PathItem::readOperations)
                .flatMap(Collection::stream)
                .forEach(
                        operation -> {
                            ApiResponses responses = operation.getResponses();
                            responses.addApiResponse("400", NOT_FOUND_RESPONSE);
                            responses.addApiResponse("404", BAD_REQUEST_RESPONSE);
                            responses.addApiResponse("500", SERVER_ERROR_RESPONSE);
                        }
                );
    }

    private void addGenericErrorResponseSchema(OpenAPI openApi) {
        Components components = openApi.getComponents();

        if (components == null) {
            components = new Components();
            openApi.setComponents(components);
        }

        components.addSchemas("GenericErrorResponse", GENERIC_ERROR_RESPONSE_SCHEMA);
    }


}
