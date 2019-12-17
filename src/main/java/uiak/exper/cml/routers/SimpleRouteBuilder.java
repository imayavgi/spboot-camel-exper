package uiak.exper.cml.routers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:incoming")
        .routeId("simplesvc")
        .startupOrder(4)
        .bean("simpleService")
        .process((exchange) ->
        {
            System.out.println(exchange.getIn().getHeaders());
            System.out.println(exchange.getIn().getBody(String.class));
            exchange.getIn().setBody(exchange.getIn().getBody(String.class).toString().toUpperCase());
        })
        //.to("direct:out");
        .to("stream:out");
    }
}