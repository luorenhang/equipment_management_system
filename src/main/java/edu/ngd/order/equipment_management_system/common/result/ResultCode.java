package edu.ngd.order.equipment_management_system.common.result;

/**
 * 结果码枚举类
 */
public enum ResultCode {
    
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    
    /**
     * 失败
     */
    FAIL(500, "失败"),
    
    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),
    
    /**
     * 未找到资源
     */
    NOT_FOUND(404, "未找到资源"),
    
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    
    /**
     * 设备不存在
     */
    EQUIPMENT_NOT_EXIST(1001, "设备不存在"),
    
    /**
     * 设备编码已存在
     */
    EQUIPMENT_CODE_EXIST(1002, "设备编码已存在"),
    
    /**
     * 点检记录不存在
     */
    INSPECTION_NOT_EXIST(2001, "点检记录不存在"),
    
    /**
     * 保养记录不存在
     */
    MAINTENANCE_NOT_EXIST(3001, "保养记录不存在"),
    
    /**
     * 故障代码不存在
     */
    FAULT_CODE_NOT_EXIST(4001, "故障代码不存在"),
    
    /**
     * 故障代码已存在
     */
    FAULT_CODE_EXIST(4002, "故障代码已存在"),
    
    /**
     * 维修记录不存在
     */
    REPAIR_NOT_EXIST(5001, "维修记录不存在");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 消息
     */
    private final String message;
    
    /**
     * 构造方法
     * @param code 状态码
     * @param message 消息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * 获取状态码
     * @return 状态码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 获取消息
     * @return 消息
     */
    public String getMessage() {
        return message;
    }
}