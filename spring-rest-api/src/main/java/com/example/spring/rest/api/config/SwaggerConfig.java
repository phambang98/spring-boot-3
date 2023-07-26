package com.example.spring.rest.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring-Boot-3")
                        .description("Spring-Boot-3")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring-Boot-3 source code")
                        .url("https://github.com/phambang98/spring-boot-3"))
                .schemaRequirement("bearerAuth", securitySchemeJwt())
                .schemaRequirement("basicAuth", securitySchemeBasic())
                .schemaRequirement("GoogleOauth2", securitySchemeOauth2Google());
    }

    private SecurityScheme securitySchemeJwt() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer").bearerFormat("JWT");
    }

    private SecurityScheme securitySchemeBasic() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).in(SecurityScheme.In.HEADER).scheme("basic");
    }

    private SecurityScheme securitySchemeOauth2Google() {
        OAuthFlow oAuthFlowObject = new OAuthFlow();
        oAuthFlowObject
                .setAuthorizationUrl("https://accounts.google.com/o/oauth2/v2/auth");
        oAuthFlowObject.setTokenUrl("https://oauth2.googleapis.com/token");
        oAuthFlowObject.setScopes(new Scopes().addString("email", "").addString("profile",""));

        OAuthFlows oAuthFlows = new OAuthFlows();
        oAuthFlows.authorizationCode(oAuthFlowObject);
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2).in(SecurityScheme.In.HEADER).flows(oAuthFlows);
    }

}
