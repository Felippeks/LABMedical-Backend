package br.com.senai.lab365.labmedical.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi pacienteApi() {
        return GroupedOpenApi.builder()
                .group("pacientes")
                .pathsToMatch("/api/pacientes/**")
                .build();
    }
    @Bean
    public GroupedOpenApi consultaApi() {
        return GroupedOpenApi.builder()
                .group("consultas")
                .pathsToMatch("/api/consultas/**")
                .build();
    }

    @Bean
    public GroupedOpenApi exameApi() {
        return GroupedOpenApi.builder()
                .group("exames")
                .pathsToMatch("/api/exames/**")
                .build();
    }
}