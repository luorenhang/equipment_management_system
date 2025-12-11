package edu.ngd.order.equipment.maintenance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 设备维护管理微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.ngd.order.equipment.maintenance.mapper")
public class EquipmentMaintenanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentMaintenanceServiceApplication.class, args);
    }

}