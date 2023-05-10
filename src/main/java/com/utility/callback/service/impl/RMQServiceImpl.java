package com.utility.callback.service.impl;

import com.utility.callback.service.RMQService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Impl for Service communication
 *
 * @author Nishant Bhardwaj
 */
@Service
public class RMQServiceImpl implements RMQService {

    private Logger logger = LogManager.getLogger(RMQServiceImpl.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public String publishMessage(String message) {

        logger.info("Publish RMQ message: "+ message);

        rabbitTemplate.convertAndSend("HBCB_DEFAULT", message);

        return null;
    }
}
