package com.example.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleRoute extends RouteBuilder {

    @Override
    public void configure() {

        Processor processor2 = exchange -> exchange.getIn().setBody("Hola Mundo 2!");

        from("timer:foo?period=2000")
                .setBody().simple("Hola Mundo!")
                .to("log:bar")
                .process(processor2)
                .to("log:bar2")
                .log(LoggingLevel.WARN, "hey!")
                .choice()
                    .when(Exchange::hasOut)
                        .to("log:bar3")
                    .otherwise()
                        .to("log:bar4")
                .endChoice();
    }
}
