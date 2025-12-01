package edu.ngd.order.equipment_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * 设备管理系统应用程序入口类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EquipmentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentManagementSystemApplication.class, args);
    }
    
    /**
     * 配置Gateway路由
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 添加测试数据库连接的路由
                .route("test-db", r -> r.path("/api/test-db-connection")
                        .uri("forward:/test-db-connection"))
                .build();
    }
}
