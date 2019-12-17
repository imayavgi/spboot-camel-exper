package uiak.exper.cml.routers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JmsRecieverRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("activemq:queue:inbox")
            .routeId("jmsreciever")
            .startupOrder(1)
            .process((exchange) ->
            {
                System.out.println("JMS RX HEADERS : "+exchange.getIn().getHeaders());
                System.out.println("JMS RX BODY: "+exchange.getIn().getBody(String.class));
                String data = exchange.getIn().getBody(String.class).toString().toUpperCase().split("=")[1];
                exchange.getIn().setBody(data);
            })
            //convert csv record into List<List<String>>
            .unmarshal().csv()
            //convert List<List<String>> into csv record
            .marshal().csv()
            .to("stream:out");
        }
    }