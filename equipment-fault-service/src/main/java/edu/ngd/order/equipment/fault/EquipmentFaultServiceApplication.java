package edu.ngd.order.equipment.fault;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 设备故障管理微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.ngd.order.equipment.fault.mapper")
public class EquipmentFaultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentFaultServiceApplication.class, args);
    }

}