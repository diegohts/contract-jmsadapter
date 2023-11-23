package com.diego.contract.contractjmsadapter.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InboundMessageProcessor implements Processor {

    static final Logger log = LoggerFactory.getLogger(InboundMessageProcessor.class);

    public void process(Exchange exchange) throws Exception {
        log.info("Mensagem recebida");
        // lógica de processamento para mensagem de entrada
    }
}
