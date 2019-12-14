package uiak.exper.cml.routers;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:8000/hello")
        .process((exchange) ->
        {
            ProducerTemplate template = exchange.getContext().createProducerTemplate();
            //template.sendBodyAndHeader("direct:incoming", "Hello World", "HEADER1", "HEADER_1_VALUE");
            //template.sendBodyAndHeader("activemq:queue:inbox", "Hello World", "HEADER1", "HEADER_1_VALUE");
            template.sendBodyAndHeaders("activemq:queue:inbox", exchange.getIn().getBody(), exchange.getIn().getHeaders());
        })
        //.from("direct:out")
        //.setBody();
        .transform().simple("Hello from Camel");
    }
}