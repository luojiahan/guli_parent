package com.atguigu.oss;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
public class OssApplication {
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext run= SpringApplication.run(OssApplication.class, args);
        Environment env = run.getEnvironment();
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        System.out.println(
                StrUtil.format("\n----------------------------------------------------------\n\t" +
                                "Application 'service-oss' is running! Access URLs:\n\t" +
                                "Local: \t\thttp://localhost:{}\n\t" +
                                "External: \thttp://{}:{}\n\t" +
                                "Doc: \thttp://{}:{}/swagger-ui.html\n" +
                                "----------------------------------------------------------",
                        port, host, port, host, port));
    }
}
