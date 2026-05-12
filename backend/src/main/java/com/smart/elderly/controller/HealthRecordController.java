package com.smart.elderly.controller;

import com.smart.elderly.common.Result;
import com.smart.elderly.entity.HealthRecord;
import com.smart.elderly.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @GetMapping("/history")
    public Result<List<HealthRecord>> history() {
        return Result.success(healthRecordService.getRecordsWithNames());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody HealthRecord record) {
        boolean success = healthRecordService.saveRecord(record);
        if (success) {
            return Result.success("记录保存成功");
        } else {
            return Result.error("记录保存失败");
        }
    }
}
