package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.AcademicActivity;
import com.yyq.service.IAcademicActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/academic")
public class AcademicActivityController {
    @Autowired
    private IAcademicActivityService academicActivityService;

    // 添加活动
    @PostMapping
    public Result<String> add(@RequestBody AcademicActivity activity) {
        activity.setCreateTime(LocalDateTime.now());
        academicActivityService.save(activity);
        return Result.success("添加成功");
    }

    // 查询所有
    @GetMapping
    public Result<List<AcademicActivity>> list() {
        List<AcademicActivity> list = academicActivityService.list();
        return Result.success(list);
    }

    // 修改活动
    @PutMapping
    public Result<String> update(@RequestBody AcademicActivity activity) {
        academicActivityService.updateById(activity);
        return Result.success("更新成功");
    }

    // 删除活动
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        academicActivityService.removeById(id);
        return Result.success("删除成功");
    }

    // 查询详情
    @GetMapping("/{id}")
    public Result<AcademicActivity> getById(@PathVariable Integer id) {
        AcademicActivity activity = academicActivityService.getById(id);
        return Result.success(activity);
    }
}
