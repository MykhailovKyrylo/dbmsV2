package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import service.IDbService;
import service.ITableService;

@SpringBootApplication
public class Application {

    @Bean
    public HttpInvokerProxyFactoryBean dbServiceInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8092/dbservice");
        invoker.setServiceInterface(IDbService.class);
        return invoker;
    }

    @Bean
    public HttpInvokerProxyFactoryBean tableServiceInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8092/tableservice");
        invoker.setServiceInterface(ITableService.class);
        return invoker;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
