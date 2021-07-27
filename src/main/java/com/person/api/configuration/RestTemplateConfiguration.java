package com.person.api.configuration;

import com.person.api.exceptionhandler.RestTemplateResponseErrorHandler;
import com.person.api.util.ObjectMapperUtil;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean("restTemplate")
    @Primary
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder.errorHandler(new RestTemplateResponseErrorHandler()).build();

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(ObjectMapperUtil.getMapper());

        restTemplate.getMessageConverters().removeIf(m -> m instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(messageConverter);

        return restTemplate;
    }

}
