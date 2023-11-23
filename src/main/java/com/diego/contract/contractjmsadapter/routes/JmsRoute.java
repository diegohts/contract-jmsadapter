package com.diego.contract.contractjmsadapter.routes;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.diego.contract.contractjmsadapter.processor.InboundMessageProcessor;
import com.diego.contract.contractjmsadapter.processor.OutboundMessageProcessor;

@Component
public class JmsRoute extends RouteBuilder {

    static final Logger log = LoggerFactory.getLogger(JmsRoute.class);

    @Override
    public void configure() throws Exception {
        from("{{inbound.endpoint}}")
        .transacted()
        .process(new InboundMessageProcessor())
        .setProperty("loopIndex").constant(0)
        .loop(simple("{{outbound.loop.count}}"))
        .to("{{outbound.endpoint}}")
        .process(new OutboundMessageProcessor())
        .end();
    }
}
