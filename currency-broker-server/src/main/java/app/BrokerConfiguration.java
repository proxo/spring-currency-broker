package app;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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

    @Bean
    MetricRegistry metricsRegistry() {
        return new MetricRegistry();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BrokerConfiguration.class);

        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        JmxReporter.forRegistry(applicationContext.getBean(MetricRegistry.class))
                .build()
                .start();

    }

    @Bean
    ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }
} 