package edu.ngd.order.equipment_management_system.common.exception;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    
    /**
     * 处理自定义异常
     * @param e 自定义异常
     * @return 统一返回结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理其他异常
     * @param e 异常
     * @return 统一返回结果
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }
}