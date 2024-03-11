package br.com.marcio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.marcio.serialization.converter.YamlJacksonHttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
    @Value("${cors.originPatterns:default}")
    private String corsOriginsPatterns = "";

    @SuppressWarnings("null")
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(new YamlJacksonHttpMessageConverter());

    } 

    @SuppressWarnings("null")
    public void addCorsMappings(CorsRegistry registry){
        var allowedOrigins = corsOriginsPatterns.split(",");
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true);

    }

    /** Content negotiation via queryParament
    @SuppressWarnings("null") 
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
        
        configurer.favorParameter(true)
        .parameterName("mediaType")
        .ignoreAcceptHeader(false)
        .useRegisteredExtensionsOnly(false)
        .defaultContentType(MediaType.APPLICATION_JSON)
        .mediaType("json", MediaType.APPLICATION_JSON)
        .mediaType("xml", MediaType.APPLICATION_XML);
    }
    */
    @SuppressWarnings("null")
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MEDIA_TYPE_APPLICATION_YML);
    }

}
