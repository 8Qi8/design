package com.yyq.controller;


import com.yyq.common.result.PageResult;
import com.yyq.common.result.Result;
import com.yyq.pojo.entity.Label;
import com.yyq.service.ILabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/label")
@Slf4j
public class LabelController {

    @Autowired
    private ILabelService labelService;
    /**
     * 获取所有标签
     */
    @GetMapping
    public Result<List<Label>> getAll() {
        log.info("获取所有标签");
        List<Label> list = labelService.list();
        return Result.success(list);
    }
    /**
     * 根据id获取标签
     */
    @GetMapping("/{id}")
    public Result<Label> getById(@PathVariable Long id) {
        log.info("根据id获取标签");
        Label label = labelService.getById(id);
        return Result.success(label);
    }
    /**
     * 添加标签
     */
    @PostMapping
    public Result<String> add(@RequestBody Label label) {
        label.setCreateTime(LocalDateTime.now());
        label.setUpdateTime(LocalDateTime.now());
        labelService.save(label);
        return Result.success("添加成功");
    }
    /**
     * 修改标签
     */
    @PutMapping
    public Result<String> update(@RequestBody Label label) {
        label.setUpdateTime(LocalDateTime.now());
        labelService.updateById(label);
        return Result.success("修改成功");
    }
    /**
     * 批量删除标签
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        labelService.removeByIds(ids);
        return Result.success("删除成功");
    }
    /**
     * 分页查询标签
     */
    @GetMapping("/{currentSize}/{pageSize}")
    public PageResult getLabelPage(@PathVariable int currentSize, @PathVariable int pageSize) {
        PageResult labelPage = labelService.getLabelPage(currentSize, pageSize);
        return labelPage;
    }
    /**
     * 根据ids获取标签
     */
    @GetMapping("/ids")
    public Result<List<Label>> getLabelByIds(@RequestParam List<Long> labelIds) {
        log.info("根据ids获取标签");
        List<Label> list = labelService.listByIds(labelIds);
        return Result.success(list);
    }

}
