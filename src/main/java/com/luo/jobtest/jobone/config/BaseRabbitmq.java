package com.luo.jobtest.jobone.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author  wenwu  luo
 * Created  data on 2018-06-08.
 */
public abstract class BaseRabbitmq {

    public static final String KEY_DIRECT_EXCHANGE_NAME = "${queue.directExchange}";
    public static final String KEY_PREFIX = "${queue.prefix}";
    public static final String CONTAINER_FACTORY_MANUAL = "containerFactoryManual";
    public static final String CONTAINER_FACTORY_AUTO = "containerFactoryAuto";

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        bindings().parallelStream().forEach(queueBinding -> {
            amqpAdmin.declareExchange(queueBinding.createExchange());
            amqpAdmin.declareQueue(new Queue(queueBinding.getQueue()));
            amqpAdmin.declareBinding(queueBinding.toBinding());
        });

        return amqpAdmin;
    }

    @Bean(name = BaseRabbitmq.CONTAINER_FACTORY_MANUAL)
    public SimpleRabbitListenerContainerFactory manualAckRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(5);
        factory.setPrefetchCount(50);
        factory.setMaxConcurrentConsumers(30);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean(name = BaseRabbitmq.CONTAINER_FACTORY_AUTO)
    public SimpleRabbitListenerContainerFactory autoAckRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(5);
        factory.setPrefetchCount(50);
        factory.setMaxConcurrentConsumers(30);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * bind queue
     * @return
     */
    protected abstract List<QueueBinding> bindings();

}
