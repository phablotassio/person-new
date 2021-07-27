package com.person.api.exceptionhandler;

import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.JsonConvertException;
import com.person.api.util.ObjectMapperUtil;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        try {
            ApiError apiError = ObjectMapperUtil.toObject(IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8), ApiError.class);
            final List<String> collect = apiError.getMessages().stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                throw new AbstractRuntimeException(collect, httpResponse.getStatusCode());
            }

        } catch (JsonConvertException e) {
            LOGGER.error(e.getMessage());
        }

        throw new AbstractRuntimeException(httpResponse.getStatusCode());
    }
}
