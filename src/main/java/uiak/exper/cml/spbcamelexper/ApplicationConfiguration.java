package uiak.exper.cml.spbcamelexper;

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

    @Bean
    public  JmsConfiguration jmsConfiguration() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        JmsConfiguration config = new JmsConfiguration();
        config.setConnectionFactory(factory);
        return config;
    }

    @Bean
    public ActiveMQComponent activemq(JmsConfiguration jmsConfiguration) {
        ActiveMQComponent comp = new ActiveMQComponent();
        comp.setConfiguration(jmsConfiguration);
        return comp;
    }

}