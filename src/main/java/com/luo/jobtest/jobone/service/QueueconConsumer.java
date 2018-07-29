package com.luo.jobtest.jobone.service;

import com.luo.jobtest.jobone.config.BaseRabbitmq;
import com.rabbitmq.client.Channel;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@CommonsLog
@Component
public class QueueconConsumer {

    @RabbitListener(queues = "stg-bcb6x-sys",containerFactory=BaseRabbitmq.CONTAINER_FACTORY_MANUAL)
    @RabbitHandler
    public  void   consumer(Message message, Channel channel) throws IOException {
        log.info(message.getBody().toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
