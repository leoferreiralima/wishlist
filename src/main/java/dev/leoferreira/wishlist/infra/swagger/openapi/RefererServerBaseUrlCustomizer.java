package dev.leoferreira.wishlist.infra.swagger.openapi;

import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class RefererServerBaseUrlCustomizer implements ServerBaseUrlCustomizer {

    @Override
    public String customize(String serverBaseUrl) {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader(HttpHeaders.REFERER))
                .map(this::decode)
                .map(refererRequestUrl -> getServerUrl(refererRequestUrl, serverBaseUrl))
                .orElse(serverBaseUrl);
    }

    protected String getServerUrl(String refererRequestUrl, String serverBaseUrl) {
        try {
            URL requestUrl = new URL(refererRequestUrl);
            URL serverUrl = new URL(serverBaseUrl);

            return new URI(
                    requestUrl.getProtocol(),
                    null,
                    requestUrl.getHost(),
                    requestUrl.getPort(),
                    serverUrl.getPath(),
                    serverUrl.getQuery(),
                    serverUrl.getRef()

            ).toString();
        } catch (MalformedURLException | URISyntaxException e) {
            return serverBaseUrl;
        }
    }

    private String decode(String requestURI) {
        return URLDecoder.decode(requestURI, StandardCharsets.UTF_8);
    }
}
