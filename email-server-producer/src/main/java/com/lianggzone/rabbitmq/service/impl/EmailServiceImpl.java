package com.lianggzone.rabbitmq.service.impl;

import com.lianggzone.rabbitmq.service.EmailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <h3>概要:</h3><p>EmailServiceImpl</p>
 * <h3>功能:</h3><p>Email服务实现类</p>
 * <h3>履历:</h3>
 * <li>2017年6月8日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
@Service
public class EmailServiceImpl implements EmailService{

    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Resource( name = "rabbitTemplate" )
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange}")
    private String exchange;

    @Value("${mq.routekey}")
    private String routeKey;

    @Override
    public void sendEmail(String message) throws Exception {
        try {
            rabbitTemplate.convertAndSend(exchange, routeKey, message);
        }catch (Exception e){
            logger.error("EmailServiceImpl.sendEmail", ExceptionUtils.getMessage(e));
        }
    }
}
