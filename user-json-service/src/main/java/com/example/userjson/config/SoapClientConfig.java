package com.example.userjson.config;

import com.example.userjson.client.SoapAuthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientConfig {

    @Bean
    public SoapAuthClient soapAuthClient() {
        SoapAuthClient client = new SoapAuthClient();
        // Marshaller хэрэгтэй ч энд payload-ийг гараар зохицуулж байна
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.users");
        client.setDefaultUri("http://localhost:8081/ws");
        return client;
    }
}