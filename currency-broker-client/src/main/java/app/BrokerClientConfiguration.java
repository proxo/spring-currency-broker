package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import service.BrokerServiceTester;

import java.util.Collections;

@Configuration
@ImportResource({"classpath:app-context.xml"})
@EnableAutoConfiguration(exclude = {EmbeddedServletContainerAutoConfiguration.class})
public class BrokerClientConfiguration {

    public static final Logger logger = LoggerFactory.getLogger(BrokerClientConfiguration.class);

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.setMessageConverters(Collections.singletonList(converter));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BrokerClientConfiguration.class);
        springApplication.setWebEnvironment(false);
        springApplication.setShowBanner(false);

        ConfigurableApplicationContext run = springApplication.run(args);
        BrokerServiceTester serviceTester = run.getBean(BrokerServiceTester.class);
        logger.info("Running test service");

        serviceTester.run();
    }

} 