package com.lianggzone.rabbitmq.activator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>概要:</h3><p>RunMain</p>
 * <h3>功能:</h3><p>程序入口</p>
 * <h3>履历:</h3>
 * <li>2017年6月8日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.lianggzone.rabbitmq"})
public class WebMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebMain.class, args);
    }
}