package com.smart.elderly.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.error("系统发生异常: " + e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> handleSqlException(SQLIntegrityConstraintViolationException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            return Result.error("该用户名已被注册");
        }
        return Result.error("数据库操作失败: " + e.getMessage());
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public Result<String> handleDataIntegrityException(org.springframework.dao.DataIntegrityViolationException e) {
        if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
            return Result.error("该用户名已被注册");
        }
        return Result.error("数据违反完整性约束: " + e.getMessage());
    }
}
