package edu.ngd.order.equipment.inspection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 设备巡检管理微服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("edu.ngd.order.equipment.inspection.mapper")
public class EquipmentInspectionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentInspectionServiceApplication.class, args);
    }

}