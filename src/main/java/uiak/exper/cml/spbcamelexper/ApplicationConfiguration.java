package uiak.exper.cml.spbcamelexper;

import javax.annotation.PostConstruct;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("uiak.exper.cml.routers, uiak.exper.cml.services")
public class ApplicationConfiguration {

    @Value("${activemq.broker.url}")
    private String brokerURL;

    @Autowired
    CamelContext camelContext;

    @PostConstruct
    public void camelConfigure() {
        camelContext.getShutdownStrategy().setTimeout(60);
    }

    @Bean
    public  JmsConfiguration jmsConfiguration() {
        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        final JmsConfiguration config = new JmsConfiguration();
        config.setConnectionFactory(factory);
        return config;
    }

    @Bean
    public ActiveMQComponent activemq(final JmsConfiguration jmsConfiguration) {
        final ActiveMQComponent comp = new ActiveMQComponent();
        comp.setConfiguration(jmsConfiguration);
        return comp;
    }

}