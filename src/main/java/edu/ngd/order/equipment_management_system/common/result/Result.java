package edu.ngd.order.equipment_management_system.common.result;

import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class Result<T> {
    
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 成功构造方法
     * @param data 数据
     */
    private Result(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        this.data = data;
    }
    
    /**
     * 成功构造方法
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     */
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 失败构造方法
     * @param code 状态码
     * @param message 消息
     */
    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
    
    /**
     * 成功返回
     * @param data 数据
     * @param <T> 数据类型
     * @return 统一返回结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
    
    /**
     * 成功返回
     * @param <T> 数据类型
     * @return 统一返回结果
     */
    public static <T> Result<T> success() {
        return new Result<>(null);
    }
    
    /**
     * 失败返回
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return 统一返回结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }
    
    /**
     * 失败返回
     * @param resultCode 结果码枚举
     * @param <T> 数据类型
     * @return 统一返回结果
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage());
    }
    
    /**
     * 失败返回
     * @param resultCode 结果码枚举
     * @param message 消息
     * @param <T> 数据类型
     * @return 统一返回结果
     */
    public static <T> Result<T> fail(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message);
    }
}