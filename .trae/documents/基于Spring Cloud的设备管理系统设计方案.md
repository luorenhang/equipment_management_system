# 基于单体应用的设备管理系统设计方案

## 一、系统架构设计

### 1. 架构选型
- **单体应用架构**：采用Spring Boot 2.3.12.RELEASE + Spring Cloud Hoxton.SR12
- **ORM框架**：MyBatis（已引入依赖）
- **数据库**：MySQL（已引入依赖，数据库已创建）
- **API网关**：Spring Cloud Gateway（已引入依赖）
- **服务注册与发现**：Eureka（已引入依赖）
- **配置中心**：Nacos（已配置）
- **服务监控**：Sentinel（已配置）

### 2. 系统架构图
```
┌─────────────────────────────────────────────────────────────────┐
│                     设备管理系统 (单体应用)                     │
├─────────────────┬─────────────────┬─────────────────────────────┤
│  设备台账模块   │  设备点检模块   │  设备保养模块               │
├─────────────────┼─────────────────┼─────────────────────────────┤
│  设备故障代码模块  │  设备维修模块   │  公共组件（工具类、异常处理等） │
└─────────────────┴─────────────────┴─────────────────────────────┘
        │                │                │
        └────────────────┼────────────────┘
                         ▼
                ┌─────────────────┐
                │   数据库访问层   │
                └─────────────────┘
                         │
                         ▼
                ┌─────────────────┐
                │    MySQL数据库   │
                └─────────────────┘
```

## 二、数据库设计

### 1. 设备台账表（equipment）
| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 设备ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_code | VARCHAR(50) | 设备编码 | UNIQUE, NOT NULL |
| equipment_name | VARCHAR(100) | 设备名称 | NOT NULL |
| equipment_type | VARCHAR(50) | 设备类型 | NOT NULL |
| model | VARCHAR(50) | 设备型号 | NOT NULL |
| manufacturer | VARCHAR(100) | 制造商 | NOT NULL |
| purchase_date | DATE | 购买日期 | NOT NULL |
| service_life | INT | 使用寿命（月） | NOT NULL |
| status | VARCHAR(20) | 设备状态 | NOT NULL |
| responsible_person | VARCHAR(50) | 责任人 | NOT NULL |
| location | VARCHAR(100) | 存放位置 | NOT NULL |
| description | TEXT | 设备描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

### 2. 设备点检表（equipment_inspection）
| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 点检ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_id | BIGINT | 设备ID | FOREIGN KEY (equipment_id) REFERENCES equipment(id) |
| inspection_date | DATETIME | 点检日期 | NOT NULL |
| inspector | VARCHAR(50) | 点检人 | NOT NULL |
| inspection_items | TEXT | 点检项目 | NOT NULL |
| inspection_result | VARCHAR(20) | 点检结果 | NOT NULL |
| problem_description | TEXT | 问题描述 | |
| handling_suggestion | TEXT | 处理建议 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

### 3. 设备保养表（equipment_maintenance）
| 字段名 | 数据类型 | 描述 | 约束 |
|-------|---------|------|------|
| id | BIGINT | 保养ID | PRIMARY KEY, AUTO_INCREMENT |
| equipment_id | BIGINT | 设备ID | FOREIGN KEY (equipment_id) REFERENCES equipment(id) |
| maintenance_date | DATETIME | 保养日期 | NOT NULL |
| maintenance_type | VARCHAR(50) | 保养类型 | NOT NULL |
| maintenance_items | TEXT | 保养项目 | NOT NULL |
| maintenance_person | VARCHAR(50) | 保养人 | NOT NULL |
| maintenance_result | VARCHAR(20) | 保养结果 | NOT NULL |
| next_maintenance_date | DATE | 下次保养日期 | NOT NULL |
| description | TEXT | 保养描述 | |
| create_time | DATETIME | 创建时间 | DEFAULT CURRENT_TIMESTAMP |
| update_time | DATETIME | 更新时间 | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

### 4. 设备故障代码表（equipment_fault_code）
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

### 5. 设备维修表（equipment_repair）
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

## 三、代码结构设计

### 1. 现有项目结构
```
equipment_management_system/
├── src/main/java/
│   └── edu/ngd/order/equipment_management_system/
│       ├── demos/
│       │   └── web/
│       │       └── DatabaseController.java
│       └── EquipmentManagementSystemApplication.java
│   ├── BasicController.java
│   ├── PathVariableController.java
│   └── User.java
├── src/main/resources/
│   ├── static/
│   │   └── index.html
│   ├── application.properties
│   └── mapper/ (待创建)
└── pom.xml
```

### 2. 设计后的代码结构
```
equipment_management_system/
├── src/main/java/
│   └── edu/ngd/order/equipment_management_system/
│       ├── EquipmentManagementSystemApplication.java  # 应用入口
│       ├── entity/                                    # 实体类
│       │   ├── Equipment.java                         # 设备台账实体
│       │   ├── EquipmentInspection.java              # 设备点检实体
│       │   ├── EquipmentMaintenance.java             # 设备保养实体
│       │   ├── EquipmentFaultCode.java               # 设备故障代码实体
│       │   └── EquipmentRepair.java                  # 设备维修实体
│       ├── mapper/                                    # Mapper接口
│       │   ├── EquipmentMapper.java                  # 设备台账Mapper
│       │   ├── EquipmentInspectionMapper.java       # 设备点检Mapper
│       │   ├── EquipmentMaintenanceMapper.java      # 设备保养Mapper
│       │   ├── EquipmentFaultCodeMapper.java        # 设备故障代码Mapper
│       │   └── EquipmentRepairMapper.java           # 设备维修Mapper
│       ├── service/                                   # 服务层
│       │   ├── EquipmentService.java                 # 设备台账Service
│       │   ├── EquipmentInspectionService.java      # 设备点检Service
│       │   ├── EquipmentMaintenanceService.java     # 设备保养Service
│       │   ├── EquipmentFaultCodeService.java       # 设备故障代码Service
│       │   └── EquipmentRepairService.java          # 设备维修Service
│       ├── controller/                                # 控制层
│       │   ├── EquipmentController.java              # 设备台账Controller
│       │   ├── EquipmentInspectionController.java   # 设备点检Controller
│       │   ├── EquipmentMaintenanceController.java  # 设备保养Controller
│       │   ├── EquipmentFaultCodeController.java    # 设备故障代码Controller
│       │   └── EquipmentRepairController.java       # 设备维修Controller
│       ├── common/                                    # 公共组件
│       │   ├── constant/                              # 常量类
│       │   ├── exception/                             # 异常处理
│       │   ├── result/                                # 统一返回结果
│       │   └── util/                                  # 工具类
│       └── demos/
│           └── web/
│               └── DatabaseController.java
├── src/main/resources/
│   ├── static/
│   │   └── index.html
│   ├── application.properties
│   └── mapper/                                        # MyBatis映射文件
│       ├── EquipmentMapper.xml                       # 设备台账Mapper.xml
│       ├── EquipmentInspectionMapper.xml            # 设备点检Mapper.xml
│       ├── EquipmentMaintenanceMapper.xml           # 设备保养Mapper.xml
│       ├── EquipmentFaultCodeMapper.xml             # 设备故障代码Mapper.xml
│       └── EquipmentRepairMapper.xml                # 设备维修Mapper.xml
└── pom.xml
```

## 四、核心功能设计

### 1. 设备台账模块
- 设备信息的增删改查
- 设备状态监控
- 责任人管理

### 2. 设备点检模块
- 点检记录的增删改查
- 点检结果统计分析

### 3. 设备保养模块
- 保养记录的增删改查
- 保养提醒

### 4. 设备故障代码模块
- 故障代码的增删改查
- 故障类型管理
- 故障处理方法查询

### 5. 设备维修模块
- 维修记录的增删改查
- 维修费用统计
- 维修结果分析

## 五、实现步骤

### 1. 创建实体类（Entity）
- 创建Equipment.java
- 创建EquipmentInspection.java
- 创建EquipmentMaintenance.java
- 创建EquipmentFaultCode.java
- 创建EquipmentRepair.java

### 2. 创建Mapper接口
- 创建EquipmentMapper.java
- 创建EquipmentInspectionMapper.java
- 创建EquipmentMaintenanceMapper.java
- 创建EquipmentFaultCodeMapper.java
- 创建EquipmentRepairMapper.java

### 3. 创建MyBatis映射文件
- 创建EquipmentMapper.xml
- 创建EquipmentInspectionMapper.xml
- 创建EquipmentMaintenanceMapper.xml
- 创建EquipmentFaultCodeMapper.xml
- 创建EquipmentRepairMapper.xml

### 4. 创建Service层
- 创建EquipmentService.java
- 创建EquipmentInspectionService.java
- 创建EquipmentMaintenanceService.java
- 创建EquipmentFaultCodeService.java
- 创建EquipmentRepairService.java

### 5. 创建Controller层
- 创建EquipmentController.java
- 创建EquipmentInspectionController.java
- 创建EquipmentMaintenanceController.java
- 创建EquipmentFaultCodeController.java
- 创建EquipmentRepairController.java

### 6. 创建公共组件
- 创建统一返回结果类
- 创建异常处理类
- 创建常量类
- 创建工具类

### 7. 配置MyBatis
- 配置MyBatis的Mapper扫描路径
- 配置MyBatis的映射文件路径

### 8. 测试功能
- 测试设备台账模块的增删改查功能
- 测试设备点检模块的增删改查功能
- 测试设备保养模块的增删改查功能
- 测试设备故障代码模块的增删改查功能
- 测试设备维修模块的增删改查功能

## 六、API设计

### 1. 设备台账API
| 请求方法 | 请求路径 | 功能描述 |
|---------|---------|---------|
| POST | /api/equipment | 创建设备 |
| GET | /api/equipment/{id} | 查询设备详情 |
| GET | /api/equipment | 查询设备列表 |
| PUT | /api/equipment/{id} | 更新设备 |
| DELETE | /api/equipment/{id} | 删除设备 |

### 2. 设备点检API
| 请求方法 | 请求路径 | 功能描述 |
|---------|---------|---------|
| POST | /api/inspection | 创建点检记录 |
| GET | /api/inspection/{id} | 查询点检记录详情 |
| GET | /api/inspection | 查询点检记录列表 |
| PUT | /api/inspection/{id} | 更新点检记录 |
| DELETE | /api/inspection/{id} | 删除点检记录 |

### 3. 设备保养API
| 请求方法 | 请求路径 | 功能描述 |
|---------|---------|---------|
| POST | /api/maintenance | 创建保养记录 |
| GET | /api/maintenance/{id} | 查询保养记录详情 |
| GET | /api/maintenance | 查询保养记录列表 |
| PUT | /api/maintenance/{id} | 更新保养记录 |
| DELETE | /api/maintenance/{id} | 删除保养记录 |

### 4. 设备故障代码API
| 请求方法 | 请求路径 | 功能描述 |
|---------|---------|---------|
| POST | /api/fault-code | 创建故障代码 |
| GET | /api/fault-code/{id} | 查询故障代码详情 |
| GET | /api/fault-code | 查询故障代码列表 |
| PUT | /api/fault-code/{id} | 更新故障代码 |
| DELETE | /api/fault-code/{id} | 删除故障代码 |

### 5. 设备维修API
| 请求方法 | 请求路径 | 功能描述 |
|---------|---------|---------|
| POST | /api/repair | 创建维修记录 |
| GET | /api/repair/{id} | 查询维修记录详情 |
| GET | /api/repair | 查询维修记录列表 |
| PUT | /api/repair/{id} | 更新维修记录 |
| DELETE | /api/repair/{id} | 删除维修记录 |

## 七、技术要点

1. **ORM映射**：使用MyBatis实现实体类与数据库表的映射
2. **事务管理**：使用Spring的声明式事务管理
3. **异常处理**：统一的异常处理机制
4. **日志管理**：使用SLF4J + Logback实现日志管理
5. **代码规范**：遵循阿里巴巴Java开发规范
6. **API设计**：RESTful风格的API设计
7. **参数校验**：使用JSR-303实现参数校验
8. **分页查询**：实现分页查询功能

## 八、预期效果

1. 实现设备管理的全流程数字化
2. 提高设备管理的效率和准确性
3. 实现设备状态的实时监控
4. 为设备维护提供数据支持
5. 实现各模块之间的数据共享和协同工作

## 九、后续优化方向

1. 引入缓存：Redis
2. 实现设备状态的实时监控：WebSocket
3. 引入大数据分析：设备故障预测
4. 实现设备管理的移动端应用
5. 引入微服务架构：逐步拆分为微服务
6. 引入配置中心：Nacos Config
7. 引入服务熔断和降级：Sentinel
8. 引入分布式事务：Seata

以上是基于单体应用的设备管理系统的设计方案，包含了系统架构、数据库设计、代码结构设计和核心功能设计。该方案充分利用了现有的项目依赖和配置，同时考虑了系统的可扩展性和可维护性。