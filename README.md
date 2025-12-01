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

### 3.1 数据库表结构概览

本系统采用MySQL数据库，设计了11张核心业务表，涵盖设备全生命周期管理。所有表均以`Cxr_`为前缀，确保命名规范统一。

| 表名 | 中文名称 | 描述 |
|------|----------|------|
| BasicObject | 基础对象 | 系统基础表，存储所有对象的通用属性 |
| Cxr_EquipmentLedger | 设备台账 | 存储设备的基本信息和状态 |
| Cxr_EquipmentMaintain | 设备保养 | 记录设备保养计划和执行情况 |
| Cxr_EquipmentRepair | 设备维修 | 记录设备故障维修的详细信息 |
| Cxr_Check | 设备点检 | 记录设备点检计划和执行结果 |
| Cxr_CheckProject | 设备点检项目 | 记录点检计划包含的具体项目 |
| Cxr_MaintainProject | 保养项目 | 记录保养计划包含的具体项目 |
| Cxr_CheckEquipment | 设备点检项目 | 定义设备的点检项目和标准 |
| Cxr_EquipmentCode | 设备故障代码 | 定义设备故障的标准代码和描述 |
| Cxr_Annex | 附件表 | 存储设备相关的附件信息 |
| Cxr_PersonResponsible | 设备负责人 | 记录设备的责任人信息 |

### 3.2 详细表结构设计

#### 3.2.1 基础对象表（BasicObject）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| Tenant | 租户 | 整型 | - | 租户ID |
| RdmVersion | 系统版本 | 整型 | - | 数据版本号 |
| RdmExtensionType | 实体 | 文本 | 50 | 实体类型 |
| ClassName | 类名 | 文本 | 50 | 类名称 |
| CreateTime | 创建时间 | 日期 | - | 记录创建时间 |
| Creator | 创建人 | 文本 | 50 | 创建人姓名 |
| LastUpdateTime | 最后更新时间 | 日期 | - | 记录最后更新时间 |
| Modifier | 更新人 | 文本 | 50 | 最后更新人姓名 |

#### 3.2.2 设备台账表（Cxr_EquipmentLedger）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| StorageLocation | 存放地点 | 文本 | - | 从数据字典取数(对象) |
| Station | 工位 | 文本 | 30 | 设备所在工位 |
| EquipmentName | 设备名称 | 文本 | 100 | 设备名称 |
| EquipmentName | 部门 | 文本 | - | 从数据字典取数(对象) |
| EquipmentNumber | 设备编号 | 文本 | 100 | 设备唯一编号 |
| AssetNumber | 资产编号 | 文本 | 100 | 资产编号 |
| ProductionLine | 产线 | 文本 | - | 从数据字典取数(对象) |
| Notes | 备注 | 文本 | 100 | 备注信息 |
| Status | 状态 | 文本 | 2 | 设备状态 |
| WorkCenter | 工作中心 | 文本 | - | 从数据字典取数(对象) |
| AutomaticData | 是否自动采集 | 文本 | 2 | 是否自动采集数据 |
| Specification | 规格型号 | 文本 | 100 | 设备规格型号 |
| DeviceType | 设备类型 | 文本 | - | 从数据字典取数(对象) |
| EquipmentStatus | 设备状态 | 文本 | 2 | 设备运行状态 |
| BarCode | 条码 | 文本 | 100 | 设备条码 |

#### 3.2.3 设备保养表（Cxr_EquipmentMaintain）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| MaintainNumber | 保养编号 | 文本 | 100 | 保养记录编号 |
| EnableStatus | 启用状态 | 文本 | 2 | 是否启用 |
| OperateEndTime | 操作结束时间 | 日期 | - | 实际操作结束时间 |
| OperateStartTime | 操作开始时间 | 日期 | - | 实际操作开始时间 |
| DeviceType | 设备类型 | 文本 | 100 | 设备类型 |
| EquipmentNumber | 设备编号 | 文本 | 100 | 关联设备编号 |
| MaintainUser | 保养人 | 文本 | 100 | 执行保养人员 |
| Status | 状态 | 文本 | 2 | 保养记录状态 |
| StartTime | 开始时间 | 日期 | - | 计划开始时间 |
| EndTime | 结束时间 | 日期 | - | 计划结束时间 |
| BarCode | 条码 | 文本 | 100 | 设备条码 |
| MaintainResult | 保养结果 | 文本 | 10 | 保养执行结果 |
| EquipmentName | 设备名称 | 文本 | 100 | 设备名称 |
| MaintainDept | 责任部门 | 文本 | 100 | 责任部门 |

#### 3.2.4 设备维修表（Cxr_EquipmentRepair）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| WorkTime | 派工时间 | 日期 | - | 派工时间 |
| Urgency | 紧急程度 | 整型 | - | 维修紧急程度 |
| RepairEndTime | 维修结束时间 | 日期 | - | 实际维修结束时间 |
| RepairStartTime | 维修开始时间 | 日期 | - | 实际维修开始时间 |
| RepairCode | 故障代码 | 文本 | 100 | 关联故障代码 |
| faultDes | 故障描述 | 文本 | 225 | 故障详细描述 |
| RepairResult | 是否修复 | 整型 | - | 修复状态 |
| Status | 状态 | 整型 | - | 维修记录状态 |
| EquipmentNumber | 设备编号 | 文本 | 100 | 关联设备编号 |
| RepairStatus | 维修状态 | 整型 | - | 维修进度状态 |
| BarCode | 维修条码 | 文本 | 100 | 维修记录条码 |
| faultReason | 故障原因 | 文本 | 225 | 故障原因分析 |
| RepairExplain | 维修情况说明 | 文本 | 225 | 维修过程说明 |
| HandleMethod | 处理方式 | 文本 | 100 | 故障处理方式 |
| RepairUser | 维修人员 | 文本 | 100 | 执行维修人员 |
| EquipmentName | 设备名称 | 文本 | 100 | 设备名称 |

#### 3.2.5 设备点检表（Cxr_Check）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| CheckNumber | 点检编号 | 文本 | 100 | 点检记录编号 |
| EndTime | 计划结束时间 | 日期 | - | 计划点检结束时间 |
| StartTime | 计划开始时间 | 日期 | - | 计划点检开始时间 |
| OperateStartTime | 实际开始时间 | 日期 | - | 实际点检开始时间 |
| OperateEndTime | 实际结束时间 | 日期 | - | 实际点检结束时间 |
| EquipmentName | 设备名称 | 文本 | 100 | 设备名称 |
| EquipmentNumber | 设备编号 | 文本 | 100 | 关联设备编号 |
| DeviceType | 设备类型 | 文本 | 100 | 设备类型 |
| CheckResult | 点检结果 | 文本 | 10 | 点检执行结果 |
| CheckUser | 点检人 | 文本 | 100 | 执行点检人员 |
| EnableStatus | 启用状态 | 文本 | 2 | 是否启用 |
| BarCode | 条码 | 文本 | 100 | 设备条码 |
| Status | 状态 | 文本 | 10 | 点检记录状态 |

#### 3.2.6 设备点检项目表（Cxr_CheckProject）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| CheckId | 点检ID | 整型 | - | 关联点检记录ID |
| Reason | 原因 | 文本 | 225 | 异常原因 |
| Remark | 备注 | 文本 | 225 | 备注信息 |
| status | 状态 | 整型 | - | 项目状态 |
| CheckProject | 点检项目 | 文本 | 225 | 点检项目名称 |
| CheckContent | 点检内容 | 文本 | 225 | 点检具体内容 |
| CheckResult | 点检结果 | 文本 | 10 | 点检结果 |
| CheckStandard | 点检标准 | 文本 | 225 | 点检判断标准 |

#### 3.2.7 保养项目表（Cxr_MaintainProject）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| MaintainStandard | 保养标准 | 文本 | 225 | 保养执行标准 |
| MaintainProject | 保养项目 | 文本 | 225 | 保养项目名称 |
| MaintainResult | 保养结果 | 文本 | 225 | 保养执行结果 |
| MaintainContent | 保养内容 | 文本 | 225 | 保养具体内容 |
| Reason | 原因 | 文本 | 225 | 异常原因 |
| MaintainId | 保养ID | 整型 | - | 关联保养记录ID |
| Remark | 备注 | 文本 | 225 | 备注信息 |

#### 3.2.8 设备点检项目定义表（Cxr_CheckEquipment）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| CheckType | 点检类型 | 文本 | 100 | 点检类型 |
| CheckContent | 点检内容 | 文本 | 225 | 点检具体内容 |
| EquipmentId | 设备ID | 整型 | - | 关联设备ID |
| CheckProject | 点检项目 | 文本 | 225 | 点检项目名称 |
| CheckStandard | 点检标准 | 文本 | 225 | 点检判断标准 |
| Remark | 备注 | 文本 | 225 | 备注信息 |

#### 3.2.9 设备故障代码表（Cxr_EquipmentCode）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| CodeDescribe | 故障描述 | 文本 | 225 | 故障详细描述 |
| status | 状态 | 整型 | - | 代码状态 |
| Code | 故障代码 | 文本 | 225 | 故障标准代码 |
| CodeRemark | 备注 | 文本 | 225 | 备注信息 |

#### 3.2.10 附件表（Cxr_Annex）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| AnnexName | 附件名称 | 文本 | 225 | 附件文件名称 |
| Notes | 备注 | 文本 | 225 | 备注信息 |
| AnnexRemarks | 附件说明 | 文本 | 225 | 附件用途说明 |
| EquipmentLedger | 设备台账ID | 整型 | - | 关联设备台账ID |
| AnnexAddress | 附件地址 | 文本 | 2000 | 附件存储路径 |

#### 3.2.11 设备负责人表（Cxr_PersonResponsible）

| 字段名 | 中文名称 | 数据类型 | 长度 | 描述 |
|--------|----------|----------|------|------|
| ID | 主键 | 整型 | - | 唯一标识符 |
| ResponsibleType | 责任人分类 | 文本 | 225 | 责任人类型 |
| Notes | 备注 | 文本 | 225 | 备注信息 |
| ResponsiblePerson | 责任人 | 文本 | 225 | 责任人姓名 |
| EquipmentLedger | 设备台账ID | 整型 | - | 关联设备台账ID |

### 3.3 表关系说明

1. **设备台账与负责人关系**：一个设备可以有多个负责人，通过`Cxr_PersonResponsible.EquipmentLedger`关联`Cxr_EquipmentLedger.ID`
2. **设备台账与附件关系**：一个设备可以有多个附件，通过`Cxr_Annex.EquipmentLedger`关联`Cxr_EquipmentLedger.ID`
3. **设备与维修关系**：一个设备可以有多个维修记录，通过`Cxr_EquipmentRepair.EquipmentNumber`关联`Cxr_EquipmentLedger.EquipmentNumber`
4. **设备与保养关系**：一个设备可以有多个保养记录，通过`Cxr_EquipmentMaintain.EquipmentNumber`关联`Cxr_EquipmentLedger.EquipmentNumber`
5. **设备与点检关系**：一个设备可以有多个点检记录，通过`Cxr_Check.EquipmentNumber`关联`Cxr_EquipmentLedger.EquipmentNumber`
6. **点检与点检项目关系**：一个点检记录包含多个点检项目，通过`Cxr_CheckProject.CheckId`关联`Cxr_Check.ID`
7. **保养与保养项目关系**：一个保养记录包含多个保养项目，通过`Cxr_MaintainProject.MaintainId`关联`Cxr_EquipmentMaintain.ID`
8. **设备与点检项目定义关系**：一个设备可以定义多个点检项目，通过`Cxr_CheckEquipment.EquipmentId`关联`Cxr_EquipmentLedger.ID`
9. **维修与故障代码关系**：一个维修记录关联一个故障代码，通过`Cxr_EquipmentRepair.RepairCode`关联`Cxr_EquipmentCode.Code`
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

