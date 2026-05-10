package com.smart.elderly.controller;

import com.smart.elderly.common.Result;
import com.smart.elderly.entity.Elderly;
import com.smart.elderly.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/elderly")
public class ElderlyController {

    @Autowired
    private ElderlyService elderlyService;

    @GetMapping("/list")
    public Result<List<Elderly>> list() {
        return Result.success(elderlyService.list());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Elderly elderly) {
        elderlyService.save(elderly);
        return Result.success("添加成功");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Elderly elderly) {
        elderlyService.updateById(elderly);
        return Result.success("修改成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        elderlyService.removeById(id);
        return Result.success("删除成功");
    }
}
