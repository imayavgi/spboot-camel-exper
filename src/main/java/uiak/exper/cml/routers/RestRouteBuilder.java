package uiak.exper.cml.routers;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.ShutdownRoute;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:8000/data")
        .routeId("csvdatarest")
        .startupOrder(2)
        .process((exchange) ->
        {
            final ProducerTemplate template = exchange.getContext().createProducerTemplate();
            //template.sendBodyAndHeader("direct:incoming", "Hello World", "HEADER1", "HEADER_1_VALUE");
            //template.sendBodyAndHeader("activemq:queue:inbox", "Hello World", "HEADER1", "HEADER_1_VALUE");
            template.sendBodyAndHeaders("activemq:queue:inbox", exchange.getIn().getBody(), exchange.getIn().getHeaders());
        })
        //.from("direct:out")
        //.setBody();
        .transform().simple("Hello from Camel");

        from("jetty:http://localhost:8000/terminate")        
        .routeId("terminaterest")
        .startupOrder(3)
        .process((exchange) ->
        {
            Thread stop = new Thread() {
                @Override
                public void run() {
                    try {
                        //exchange.getContext().stopRoute("myRoute");
                    exchange.getContext().getShutdownStrategy().setTimeout(5);
                    exchange.getContext().stop();
                    System.exit(0);
                    } catch (Exception e) {
                        // ignore
                    }
                }
            };
            stop.start();            
        })
        .transform().simple("Shutdown started");
    }
}