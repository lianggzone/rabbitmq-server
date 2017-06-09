package com.lianggzone.rabbitmq.activator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * <h3>概要:</h3><p>RunMain</p>
 * <h3>功能:</h3><p>程序入口</p>
 * <h3>履历:</h3>
 * <li>2017年6月8日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lianggzone.rabbitmq"})
@PropertySource(value={"classpath:application.properties"})
public class RunMain {

    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }
}