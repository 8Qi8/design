package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.Columns;

import java.util.List;
import java.util.Map;

public interface IColumnService extends IService<Columns> {
    List<Columns> listByUserId(Long userId);

    Map<Long, Long> getArticleCountsByColumnIds(List<Long> columnIds);
}
