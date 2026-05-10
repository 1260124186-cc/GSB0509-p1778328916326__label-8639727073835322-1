package com.smart.elderly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("health_records")
public class HealthRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer elderlyId;
    private String bloodPressure;
    private BigDecimal temperature;
    private Boolean isAbnormal;
    private LocalDateTime checkTime;

    @TableField(exist = false)
    private String elderlyName;
}
