package com.luo.jobtest.jobone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author by zhiHua Chen on 2018-05-07.
 */
@Configuration
public class Rabbitmq extends BaseRabbitmq {

    public static final String KEY_BCB_6X_SYS_NAME = "${queue.directNames.bcb6xSys}";

    @Value(BaseRabbitmq.KEY_DIRECT_EXCHANGE_NAME)
    String directExchangeName;
    @Value(BaseRabbitmq.KEY_PREFIX)
    String queuePrefix;

    /**
     * 注册添加6x用户队列
     */
    @Value(KEY_BCB_6X_SYS_NAME)
    public String keyBcb6xSysQueueName;

    @Override
    protected List<QueueBinding> bindings() {
        List<QueueBinding> bindings = new ArrayList<>();
        bindings.add(QueueBinding.builder().prefix(queuePrefix)
                .exchange(directExchangeName).queue(keyBcb6xSysQueueName).build());
        return bindings;
    }

}
