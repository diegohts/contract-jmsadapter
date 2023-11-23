package com.diego.contract.contractjmsadapter.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OutboundMessageProcessor implements Processor {
    static final Logger log = LoggerFactory.getLogger(OutboundMessageProcessor.class);

    public void process(Exchange exchange) throws Exception {
        int loopIndex = exchange.getProperty("loopIndex", Integer.class);

        log.info("Conteudo da mensagem enviada: {}", exchange.getIn().getBody());
        // lógica de processamento para mensagem de saída
        exchange.setProperty("loopIndex", loopIndex + 1);
        log.info("Mensagem enviada - Iteracao: {}", loopIndex);
    }
}
