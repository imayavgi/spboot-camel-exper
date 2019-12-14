package uiak.exper.cml.routers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JmsRecieverRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("activemq:queue:inbox")
            .process((exchange) ->
            {
                System.out.println("JMS RX HEADERS : "+exchange.getIn().getHeaders());
                System.out.println("JMS RX BODY: "+exchange.getIn().getBody(String.class));
                exchange.getIn().setBody(exchange.getIn().getBody(String.class).toString().toUpperCase());
            })
            .to("stream:out");
        }
    }