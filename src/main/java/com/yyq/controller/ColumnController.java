package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.Columns;
import com.yyq.service.IColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/columns")
public class ColumnController {
    @Autowired
    private IColumnService columnService;
    /**
     * 添加专栏
     */
    @PostMapping("/{id}")
    public Result<String> addColumn(@PathVariable Long id, @RequestBody Columns column){
        log.info("添加专栏");
        column.setUserId(id);
        columnService.save(column);
        return Result.success("添加成功");
    }
    /**
     * 修改专栏
     */
    @PutMapping
    public Result<String> update(@RequestBody Columns column) {
        log.info("修改专栏:{}", column);
        columnService.updateById(column);
        return Result.success("修改成功");
    }
    /**
     * 根据专栏ID查询单个专栏
     */
    @GetMapping("/{id}")
    public Result<Columns> getById(@PathVariable Long id) {
        log.info("查询单个专栏:{}", id);
        Columns column = columnService.getById(id);
        return column != null ?
                Result.success(column) :
                Result.error("未找到该专栏");
    }
    /**
     * 查询某个用户所有专栏
     */
    @GetMapping("/user/{userId}")
    public Result<List<Columns>> getByUserId(@PathVariable Long userId) {
        log.info("查询用户所有专栏:{}", userId);
        List<Columns> columns = columnService.listByUserId(userId);
        return Result.success(columns);
    }
    /**
     * 删除专栏
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        log.info("删除专栏:{}", id);
        // 检查是否存在关联文章
        Map<Long, Long> counts = columnService.getArticleCountsByColumnIds(Collections.singletonList(id));
        if (counts.getOrDefault(id, 0L) > 0) {
            return Result.error("该专栏下存在文章，无法删除");
        }

        // 执行删除操作
        boolean removed = columnService.removeById(id);
        return removed ?
                Result.success("删除成功") :
                Result.error("专栏不存在或删除失败");
    }

    /**
     * 批量查询专栏文章数量
     *
     * @param columnIds 专栏ID列表
     * @return 专栏ID与文章数量的映射关系
     */
    @PostMapping("/counts")
    public Result<Map<Long, Long>> getArticleCounts(@RequestBody List<Long> columnIds) {
        log.info("批量查询专栏文章数量: {}", columnIds);
        Map<Long, Long> counts = columnService.getArticleCountsByColumnIds(columnIds);
        return Result.success(counts);
    }



}
