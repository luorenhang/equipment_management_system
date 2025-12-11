package edu.ngd.order.equipment.repair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 设备维修管理微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.ngd.order.equipment.repair.mapper")
public class EquipmentRepairServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentRepairServiceApplication.class, args);
    }

}