package com.luo.jobtest.jobone.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.*;

/**
 * @author  wenwu  luo
 * Created  data on 2018-06-08.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueBinding {

    String exchange;
    String routingKey;
    String queue;
    String exchangeType;
    Boolean durable;
    boolean autoDelete;
    String prefix;

    public QueueBinding(String exchange, String routingKey, String queue) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queue = queue;
    }

    public Binding toBinding() {
        return new Binding(getQueue(), Binding.DestinationType.QUEUE, getExchange(), getRoutingKey(), null);
    }

    public Exchange createExchange() {
        switch (this.getExchangeType()) {
            case ExchangeTypes.DIRECT:
                return new DirectExchange(getExchange());
            case ExchangeTypes.FANOUT:
                return new FanoutExchange(getExchange());
            case ExchangeTypes.TOPIC:
                return new TopicExchange(getExchange());
                default:
        }

        return null;
    }

    public String getExchangeType() {
        return exchangeType == null ? ExchangeTypes.DIRECT : exchangeType;
    }

    public boolean getDurable() {
        return durable == null ? true : durable;
    }

    public String getPrefix() {
        return prefix == null ? "" : prefix;
    }

    public String getExchange() {
        return getPrefix() + exchange;
    }

    public String getQueue() {
        return getPrefix() + queue;
    }

    public String getRoutingKey() {
        return routingKey == null ? queue : routingKey;
    }
}
