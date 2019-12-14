package uiak.exper.cml.routers;

public class JmsReciverRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("activemq:queue:messsagerx")
            .process((exchange) ->
            {
                System.out.println(exchange.getIn().getHeaders());
                System.out.println(exchange.getIn().getBody(String.class));
                exchange.getIn().setBody(exchange.getIn().getBody(String.class).toString().toUpperCase());
            })
            .to("stream:out");
        }
    }