设备管理系统微服务设计报告

1. 项目概述

1.1 项目背景
基于Spring Cloud的设备管理系统开发实训实训系统分为设备台账、设备点检、设备保养、设备故障代码及设备维修。设备台账实现监控
和管理生产设备的详细信息和状态。 设备点检实现对设备进行定期、系统化的检查，以确保设备正常运行、预防故障和延长设备使用寿命。
设备保养实现对设备定期系统化的保养，并且形成系统记录。 设备故障代码实现对生产设备的故障类型进行分类并进行标准化的编号。设备
维修实现对生产设备进行保养、检查和修复的过程。
1.2 目标
* 实现各业务模块的独立部署和扩展
* 降低服务之间的耦合度，提高内聚性
* 支持服务的弹性伸缩
* 提高系统的可靠性和可用性
* 便于团队协作开发
* 支持服务的注册与发现、配置管理、服务监控等微服务基础设施

2. 微服务架构设计

 2.1 架构选型

**微服务架构**：采用Spring Boot 2.3.12.RELEASE + Spring Cloud Hoxton.SR12
**ORM框架**：MyBatis
**数据库**：MySQL
**API网关**：Spring Cloud Gateway
**服务注册与发现**：Nacos
**配置中心**：Nacos
**服务监控**：Sentinel

2.2 微服务拆分设计

根据业务功能和领域驱动设计原则，将项目拆分为以下微服务：

2.2.1 核心业务微服务

1. **设备管理微服务 (equipment-service)**
   * 负责设备基本信息的增删改查
   * 端口：8081

2. **设备故障管理微服务 (equipment-fault-service)**
   * 负责设备故障码的管理
   * 端口：8082

3. **设备巡检管理微服务 (equipment-inspection-service)**
   * 负责设备巡检计划和记录的管理
   * 端口：8083

4. **设备维护管理微服务 (equipment-maintenance-service)**
   * 负责设备维护计划和记录的管理
   * 端口：8084

5. **设备维修管理微服务 (equipment-repair-service)**
   * 负责设备维修记录的管理
   * 端口：8085

2.2.2 基础设施微服务

1. **网关微服务 (gateway-service)**
   * 负责请求路由、负载均衡和认证授权
   * 端口：8086

2. **注册中心与配置中心**
   * 使用 Nacos 作为注册中心和配置中心
   * 端口：8848

3. **服务监控**
   * 使用 Sentinel 作为服务监控和熔断降级工具
   * 端口：8848

2.3 系统架构图
┌─────────────────────────────────────────────────────────────────┐
│                     设备管理系统 (微服务架构)                   │
├─────────────────┬─────────────────┬─────────────────────────────┤
│  设备管理服务   │  故障管理服务   │  巡检管理服务               │
├─────────────────┼─────────────────┼─────────────────────────────┤
│  维护管理服务   │  维修管理服务   │  网关服务                   │
└─────────────────┴─────────────────┴─────────────────────────────┘
        │                │                │
        └────────────────┼────────────────┘
                         ▼
                ┌─────────────────┐
                │   Nacos 注册中心  │
                └─────────────────┘
                         │
                         ▼
                ┌─────────────────┐
                │   Nacos 配置中心  │
                └─────────────────┘
                         │
                         ▼
                ┌─────────────────┐
                │   Sentinel 监控  │
                └─────────────────┘
                         │
                         ▼
                ┌─────────────────┐
                │    MySQL数据库   │
                └─────────────────┘
## 3. 数据库设计

### 3.1 设备表（equipment）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 设备ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_code | VARCHAR(50) | 设备编码 | UNIQUE, NOT NULL |
| equipment_name | VARCHAR(100) | 设备名称 | NOT NULL |
| equipment_type_id | BIGINT | 设备类型ID | FOREIGN KEY (equipment_type_id) REFERENCES equipment_type(id) |
| model | VARCHAR(50) | 设备型号 | NOT NULL |
| manufacturer | VARCHAR(100) | 制造商 | NOT NULL |
| purchase_date | DATE | 购买日期 | NOT NULL |
| service_life | INT | 使用寿命（月） | NOT NULL |
| equipment_status_id | BIGINT | 设备状态ID | FOREIGN KEY (equipment_status_id) REFERENCES equipment_status(id) |
| responsible_id | BIGINT | 责任人ID | FOREIGN KEY (responsible_id) REFERENCES responsible(id) |
| location | VARCHAR(100) | 存放位置 | NOT NULL |
| description | TEXT | 设备描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

### 3.2 设备类型表（equipment_type）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 设备类型ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_type_name | VARCHAR(50) | 设备类型名称 | NOT NULL |
| description | TEXT | 设备类型描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.3 设备状态表（equipment_status）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 设备状态ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_status_name | VARCHAR(20) | 设备状态名称 | NOT NULL |
| description | TEXT | 设备状态描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.4 责任人表（responsible）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 责任人ID | PRIMARY KEY, AUTO_INCREMENT |
| responsible_name | VARCHAR(50) | 责任人姓名 | NOT NULL |
| responsible_phone | VARCHAR(20) | 责任人电话 | NOT NULL |
| responsible_email | VARCHAR(50) | 责任人邮箱 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.5 附件表（annex）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 附件ID | PRIMARY KEY, AUTO_INCREMENT |
| annex_name | VARCHAR(100) | 附件名称 | NOT NULL |
| annex_url | VARCHAR(255) | 附件URL | NOT NULL |
| annex_type | VARCHAR(20) | 附件类型 | NOT NULL |
| business_id | BIGINT | 业务ID | NOT NULL |
| business_type | VARCHAR(20) | 业务类型 | NOT NULL |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.6 设备故障代码表（equipment_fault_code）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 故障代码ID | PRIMARY KEY, AUTO_INCREMENT |
| fault_code | VARCHAR(20) | 故障代码 | UNIQUE, NOT NULL |
| fault_name | VARCHAR(100) | 故障名称 | NOT NULL |
| fault_type | VARCHAR(50) | 故障类型 | NOT NULL |
| fault_description | TEXT | 故障描述 | NOT NULL |
| handling_method | TEXT | 处理方法 | NOT NULL |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.7 设备巡检表（equipment_inspection）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 巡检ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_id | BIGINT | 设备ID | FOREIGN KEY (equipment_id) REFERENCES equipment(id) |
| inspection_date | DATETIME | 巡检日期 | NOT NULL |
| inspector | VARCHAR(50) | 巡检人 | NOT NULL |
| inspection_result | VARCHAR(20) | 巡检结果 | NOT NULL |
| problem_description | TEXT | 问题描述 | |
| handling_suggestion | TEXT | 处理建议 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.8 检查项目表（check_project）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 检查项目ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_inspection_id | BIGINT | 设备巡检ID | FOREIGN KEY (equipment_inspection_id) REFERENCES equipment_inspection(id) |
| check_item | VARCHAR(100) | 检查项目 | NOT NULL |
| check_result | VARCHAR(20) | 检查结果 | NOT NULL |
| check_description | TEXT | 检查描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.9 设备维护表（equipment_maintenance）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 维护ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_id | BIGINT | 设备ID | FOREIGN KEY (equipment_id) REFERENCES equipment(id) |
| maintenance_date | DATETIME | 维护日期 | NOT NULL |
| maintenance_type | VARCHAR(50) | 维护类型 | NOT NULL |
| maintenance_items | TEXT | 维护项目 | NOT NULL |
| maintenance_person | VARCHAR(50) | 维护人 | NOT NULL |
| maintenance_result | VARCHAR(20) | 维护结果 | NOT NULL |
| next_maintenance_date | DATE | 下次维护日期 | NOT NULL |
| description | TEXT | 维护描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.10 设备维修表（equipment_repair）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 维修ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_id | BIGINT | 设备ID | FOREIGN KEY (equipment_id) REFERENCES equipment(id) |
| fault_code_id | BIGINT | 故障代码ID | FOREIGN KEY (fault_code_id) REFERENCES equipment_fault_code(id) |
| repair_date | DATETIME | 维修日期 | NOT NULL |
| repair_person | VARCHAR(50) | 维修人 | NOT NULL |
| repair_items | TEXT | 维修项目 | NOT NULL |
| repair_result | VARCHAR(20) | 维修结果 | NOT NULL |
| repair_cost | DECIMAL(10,2) | 维修费用 | NOT NULL |
| description | TEXT | 维修描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

3.11 设备维修配件表（equipment_repair_accessory）

| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 维修配件ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_repair_id | BIGINT | 设备维修ID | FOREIGN KEY (equipment_repair_id) REFERENCES equipment_repair(id) |
| accessory_name | VARCHAR(100) | 配件名称 | NOT NULL |
| accessory_quantity | INT | 配件数量 | NOT NULL |
| accessory_cost | DECIMAL(10,2) | 配件单价 | NOT NULL |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |
4. 微服务代码结构设计

4.1 父项目结构
```
equipment_management_system/
├── equipment-service/              # 设备管理微服务
├── equipment-fault-service/        # 设备故障管理微服务
├── equipment-inspection-service/   # 设备巡检管理微服务
├── equipment-maintenance-service/  # 设备维护管理微服务
├── equipment-repair-service/       # 设备维修管理微服务
├── gateway-service/                # 网关微服务
├── common/                         # 公共模块
└── pom.xml                         # 父项目pom.xml
```
### 4.2 公共模块结构
```
common/
├── src/main/java/
│   └── edu/ngd/order/equipment_management_system/common/
│       ├── constant/              # 常量类
│       ├── exception/             # 异常处理
│       │   ├── BusinessException.java       # 自定义业务异常
│       │   └── GlobalExceptionHandler.java   # 全局异常处理器
│       ├── result/                # 统一返回结果
│       │   ├── Result.java         # 通用响应包装类
│       │   └── ResultCode.java     # 响应代码枚举
│       └── util/                  # 工具类
└── pom.xml
```
4.3 各微服务结构（以equipment-service为例）
```
equipment-service/
├── src/main/java/
│   └── edu/ngd/order/equipment_management_system/
│       ├── EquipmentServiceApplication.java  # 应用入口
│       ├── entity/                           # 实体类
│       │   └── Equipment.java                 # 设备台账实体
│       ├── mapper/                           # Mapper接口
│       │   └── EquipmentMapper.java          # 设备台账Mapper
│       ├── service/                          # 服务层
│       │   ├── EquipmentService.java         # 设备台账Service接口
│       │   └── impl/                         # 服务实现
│       │       └── EquipmentServiceImpl.java # 设备台账Service实现
│       └── controller/                       # 控制层
│           └── EquipmentController.java      # 设备台账Controller
├── src/main/resources/
│   ├── application.yml                       # 应用配置
│   └── mapper/                               # MyBatis映射文件
│       └── EquipmentMapper.xml               # 设备台账Mapper.xml
└── pom.xml
```
## 5. 核心功能设计
 5.1 设备管理微服务
* 设备信息的增删改查
* 设备状态监控
* 责任人管理
### 5.2 设备故障管理微服务
* 故障代码的增删改查
* 故障类型管理
* 故障处理方法查询
### 5.3 设备巡检管理微服务
* 巡检记录的增删改查
* 巡检结果统计分析
### 5.4 设备维护管理微服务
* 保养记录的增删改查
* 保养提醒
### 5.5 设备维修管理微服务
* 维修记录的增删改查
* 维修费用统计
* 维修结果分析
### 5.6 网关微服务
* 请求路由
* 负载均衡
* 认证授权
* 服务限流
## 6. API设计
### 6.1 设备管理API
| 请求方法   | 请求路径                | 功能描述   |
| ------ | ------------------- | ------ |
| POST   | /api/equipment      | 创建设备   |
| GET    | /api/equipment/{id} | 查询设备详情 |
| GET    | /api/equipment      | 查询设备列表 |
| PUT    | /api/equipment/{id} | 更新设备   |
| DELETE | /api/equipment/{id} | 删除设备   |
### 6.2 设备故障API
| 请求方法   | 请求路径                 | 功能描述     |
| ------ | -------------------- | -------- |
| POST   | /api/fault-code      | 创建故障代码   |
| GET    | /api/fault-code/{id} | 查询故障代码详情 |
| GET    | /api/fault-code      | 查询故障代码列表 |
| PUT    | /api/fault-code/{id} | 更新故障代码   |
| DELETE | /api/fault-code/{id} | 删除故障代码   |
### 6.3 设备巡检API
| 请求方法   | 请求路径                 | 功能描述     |
| ------ | -------------------- | -------- |
| POST   | /api/inspection      | 创建点检记录   |
| GET    | /api/inspection/{id} | 查询点检记录详情 |
| GET    | /api/inspection      | 查询点检记录列表 |
| PUT    | /api/inspection/{id} | 更新点检记录   |
| DELETE | /api/inspection/{id} | 删除点检记录   |
### 6.4 设备维护API
| 请求方法   | 请求路径                  | 功能描述     |
| ------ | --------------------- | -------- |
| POST   | /api/maintenance      | 创建保养记录   |
| GET    | /api/maintenance/{id} | 查询保养记录详情 |
| GET    | /api/maintenance      | 查询保养记录列表 |
| PUT    | /api/maintenance/{id} | 更新保养记录   |
| DELETE | /api/maintenance/{id} | 删除保养记录   |
### 6.5 设备维修API
| 请求方法   | 请求路径             | 功能描述     |
| ------ | ---------------- | -------- |
| POST   | /api/repair      | 创建维修记录   |
| GET    | /api/repair/{id} | 查询维修记录详情 |
| GET    | /api/repair      | 查询维修记录列表 |
| PUT    | /api/repair/{id} | 更新维修记录   |
| DELETE | /api/repair/{id} | 删除维修记录   |
## 7. 实施步骤
### 7.1 创建微服务项目结构
1. 创建父项目 pom.xml
2. 为每个微服务创建独立的 Maven 模块
3. 配置各微服务的依赖关系
### 7.2 代码拆分与迁移
1. 将原项目中的代码按业务模块拆分到对应微服务
2. 提取公共代码到 common 模块
3. 配置各微服务的数据库连接
### 7.3 微服务配置
1. 配置各微服务的 application.yml/application.properties
2. 配置 Nacos 注册中心和配置中心
3. 配置 Sentinel 监控
### 7.4 网关配置
1. 启用 Spring Cloud Gateway
2. 配置路由规则
3. 配置负载均衡
### 7.5 测试与验证
1. 启动所有微服务
2. 测试微服务之间的通信
3. 测试网关路由功能
4. 测试服务注册与发现

## 8. 技术栈

| 技术组件                 | 版本             | 用途          |
| -------------------- | -------------- | ----------- |
| Spring Boot          | 2.3.12.RELEASE | 应用框架        |
| Spring Cloud         | Hoxton.SR12    | 微服务开发框架     |
| Nacos                | -              | 服务注册中心、配置中心 |
| Spring Cloud Gateway | -              | API网关       |
| Sentinel             | -              | 服务监控、熔断降级   |
| MyBatis              | -              | ORM框架       |
| MySQL                | -              | 数据库         |

## 9. 技术要点

1. **ORM映射**：使用MyBatis实现实体类与数据库表的映射
2. **事务管理**：使用Spring的声明式事务管理
3. **异常处理**：统一的异常处理机制
4. **日志管理**：使用SLF4J + Logback实现日志管理
5. **API设计**：RESTful风格的API设计
6. **参数校验**：使用JSR-303实现参数校验
7. **分页查询**：实现分页查询功能
8. **服务注册与发现**：使用Nacos实现服务的注册与发现
9. **配置管理**：使用Nacos实现配置的集中管理
10. **服务监控**：使用Sentinel实现服务的监控和熔断降级

## 10. 预期效果

1. 实现设备管理的全流程数字化
2. 提高设备管理的效率和准确性
3. 实现设备状态的实时监控
4. 为设备维护提供数据支持
5. 实现各模块之间的数据共享和协同工作
6. 各业务模块独立部署和扩展
7. 服务之间低耦合，高内聚
8. 支持服务的弹性伸缩
9. 提高系统的可靠性和可用性
10. 便于团队协作开发

