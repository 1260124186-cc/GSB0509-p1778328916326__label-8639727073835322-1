package com.smart.elderly.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.elderly.entity.HealthRecord;
import com.smart.elderly.mapper.HealthRecordMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class HealthRecordService extends ServiceImpl<HealthRecordMapper, HealthRecord> {

    public boolean saveRecord(HealthRecord record) {
        // 预警功能：当体温超过 37.3 度时，自动标记为异常数据
        if (record.getTemperature() != null && record.getTemperature().compareTo(new BigDecimal("37.3")) > 0) {
            record.setIsAbnormal(true);
        } else {
            record.setIsAbnormal(false);
        }
        return this.save(record);
    }

    public List<HealthRecord> getRecordsWithNames() {
        return baseMapper.selectWithElderlyName();
    }
}
