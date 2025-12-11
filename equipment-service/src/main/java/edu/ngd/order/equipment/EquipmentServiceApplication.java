package edu.ngd.order.equipment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 设备管理微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.ngd.order.equipment.mapper")
public class EquipmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentServiceApplication.class, args);
    }

}