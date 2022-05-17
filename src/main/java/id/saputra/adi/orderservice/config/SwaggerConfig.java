package id.saputra.adi.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_TITLE = "REST API Docs for Customer Services";
    private static final String API_DESC = "REST API for handling Customer Services";
    private static final String API_VERSION = "v1";
    private static final String API_CONTACT_NAME = "Adi Saputra";
    private static final String API_CONTACT_EMAIL = "adisapu10022@gmail.com";
    private static final String API_NA = "N/A";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfo (
                API_TITLE, API_DESC, API_VERSION, API_NA,
                new Contact(API_CONTACT_NAME, API_NA, API_CONTACT_EMAIL),
                API_NA, API_NA, Collections.emptyList()
        );
    }
}
