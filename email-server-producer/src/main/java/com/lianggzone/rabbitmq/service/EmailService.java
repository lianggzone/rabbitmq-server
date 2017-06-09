package com.lianggzone.rabbitmq.service;

/**
 * <h3>概要:</h3><p>EmailService</p>
 * <h3>功能:</h3><p>Email服务接口</p>
 * <h3>履历:</h3>
 * <li>2017年6月8日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
public interface EmailService {

    /**
     * 发送邮件任务存入消息队列
     * @param message
     * @throws Exception
     */
    void sendEmail(String message) throws Exception;
}