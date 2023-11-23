package com.diego.contract.contractjmsadapter.config;

import javax.jms.ConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

@Configuration
public class JmsConfig {

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.activemq.broker-url}") String brokerUrl) {
        ActiveMQConnectionFactory activeMQFactory = new ActiveMQConnectionFactory();
        activeMQFactory.setBrokerURL(brokerUrl);
        // outras configurações se necessário

        // Opcional: Configurações adicionais específicas do ActiveMQConnectionFactory
        // activeMQFactory.setUserName("seuUsuario");
        // activeMQFactory.setPassword("suaSenha");

        // Opcional: Envolver em PooledConnectionFactory, se necessário
        // PooledConnectionFactory pooledFactory = new PooledConnectionFactory();
        // pooledFactory.setBrokerURL(brokerUrl);
        // pooledFactory.setCreateConnectionOnStartup(true);
        // pooledFactory.setTargetConnectionFactory(activeMQFactory);

        return activeMQFactory; // ou pooledFactory, se necessário
    }

    @Bean
    public JmsTransactionManager createJmsTransactionManager(final ConnectionFactory connectionFactory) {

        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

    @Bean
    public JmsComponent createJmsComponent(final ConnectionFactory connectionFactory,
            final JmsTransactionManager jmsTransactionManager, @Value("${max.concurrent.consumers}") final int maxConcurrentConsumers) {
        JmsComponent jmsComponent = JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
        jmsComponent.setMaxConcurrentConsumers(maxConcurrentConsumers);
        return jmsComponent;
    }
}
