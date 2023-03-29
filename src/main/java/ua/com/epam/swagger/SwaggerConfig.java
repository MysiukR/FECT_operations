package ua.com.epam.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ua.com.epam.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .displayOperationId(false)
                .docExpansion(DocExpansion.LIST)
                .operationsSorter(OperationsSorter.ALPHA)
                .defaultModelsExpandDepth(1)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST Library")
                .description("Simple REST API to reproduce Schedule")
                .version("1.0")
                .contact(new Contact("Super Team",
                        "https://github.com/Introduction-in-SWE/ScheduleFECT.git",
                        "FeP repo"))
                .build();
    }
}
