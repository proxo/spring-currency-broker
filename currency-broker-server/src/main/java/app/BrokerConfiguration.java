package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import service.CurrencyExchangeImpl;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackageClasses = {CurrencyExchangeImpl.class})
@ImportResource({"classpath:app-context.xml"})
@EnableAutoConfiguration
@EnableWebMvc
public class BrokerConfiguration {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BrokerConfiguration.class, args);
    }
} 