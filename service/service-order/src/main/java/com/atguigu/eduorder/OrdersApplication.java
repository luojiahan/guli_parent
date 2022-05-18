package com.atguigu.eduorder;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@MapperScan("com.atguigu.eduorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class OrdersApplication {
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext run= SpringApplication.run(OrdersApplication.class, args);
        Environment env = run.getEnvironment();
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        System.out.println(
                StrUtil.format("\n----------------------------------------------------------\n\t" +
                                "Application 'service-order' is running! Access URLs:\n\t" +
                                "Local: \t\thttp://localhost:{}\n\t" +
                                "External: \thttp://{}:{}\n\t" +
                                "Doc: \thttp://{}:{}/swagger-ui.html\n" +
                                "----------------------------------------------------------",
                        port, host, port, host, port));
    }
}
