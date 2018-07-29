package com.luo.jobtest.jobone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by lww on 31/7/15.
 */
@CommonsLog
@Service
public class QueueService {

    public static final String KEYDIRECTEXCHANGENAME = "${queue.directExchange}";
    public static final String KEYPREFIX = "${queue.prefix}";
    public static final String KEYPUSHNAME = "${queue.directNames.bcb6xSys}";
    public static final String HEADERACTIONTYPEKEY = "actionType";
    public static final ObjectMapper MAPPER = new ObjectMapper();
    @Value(KEYPREFIX)
    public String queuePrefix;
    @Value(KEYPUSHNAME)
    public String pushQueueName;
    @Value(KEYDIRECTEXCHANGENAME)
    public String directExchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public enum ActionType {
        /*注册设备*/
        PUSH_REGISTERDEVICE("RegisterDevice");

        private String value;
        ActionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    protected String withPrefix(String name) {
        return queuePrefix + name;
    }

    /****************** Push ****************/
    public void sendPushRegisterDeviceMessage(String userId, boolean xinge, String pushId) {
        Map<String, Object> registerDeviceMap = new HashMap<>(10);
        registerDeviceMap.put("userId", userId);
        registerDeviceMap.put("xinge", xinge);
        registerDeviceMap.put("pushId", pushId);


        sendPushMessage(ActionType.PUSH_REGISTERDEVICE, registerDeviceMap);
    }


    /**
     * @param actionType
     * @param data
     */
    public void sendPushMessage(ActionType actionType, Map data) {
        sendDirectMessageAsynchronously(pushQueueName, actionType, data);
    }




    public void sendDirectMessageAsynchronously(String queueName, ActionType actionType, Map data) {
        log.debug("Queue Name:" + queueName + " actionType:" + actionType.getValue() + " content:" + data.toString());
        try {
            sendMessage(directExchangeName, queueName, actionType.getValue(), data);

        } catch (JsonProcessingException e) {
           log.error("错误");
        }
    }


    public void sendMessage(String exchangeName, String queueName, String actionType, Map data) throws JsonProcessingException {
        if (StringUtils.hasText(actionType)) {
            data.put(HEADERACTIONTYPEKEY, actionType);
        }

        sendMessage(exchangeName, queueName, MAPPER.writeValueAsBytes(data), actionType);
    }

    public void sendMessage(String exchangeName, String routingKey, byte[] messageBody, String type) {
        Message message = MessageBuilder.withBody(messageBody)
                .setHeader(HEADERACTIONTYPEKEY, type)
                .build();

        if (log.isDebugEnabled()) {
            log.debug("sending: " + withPrefix(exchangeName) + ", " + routingKey + ", " + type);
        }

        rabbitTemplate.send(withPrefix(exchangeName), routingKey, message);
    }

}
