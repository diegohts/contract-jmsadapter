package com.diego.contract.contractjmsadapter.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JmsRoute extends RouteBuilder {

    static final Logger log = LoggerFactory.getLogger(JmsRoute.class);

    @Override
    public void configure() throws Exception {
        from("{{inbound.endpoint}}")
            .transacted()
            .log(LoggingLevel.INFO, log, "Mensagem recebida")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    log.info("Exchange: {}", exchange);
                }
            })
            .setProperty("loopIndex").constant(0) // Inicializa a variável de loopIndex
            .loop(simple("{{outbound.loop.count}}"))
            .to("{{outbound.endpoint}}")
            .log(LoggingLevel.INFO, log, "Conteudo da mensagem enviada: ${body}")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    int loopIndex = exchange.getProperty("loopIndex", Integer.class);
                    // log.info("Mensagem enviada. Iteração: {}", loopIndex);
                    exchange.setProperty("loopIndex", loopIndex + 1); // Atualiza o índice da iteração
                }
            })
            .log(LoggingLevel.INFO, log, "Mensagem enviada - Iteracao: ${exchangeProperty.loopIndex}")
            .end();
    }
}
