package uiak.exper.cml.services;

import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class SimpleService {
    @Handler
    public void process(final String data) {
        System.out.println("From Service "+ data);
    }
}