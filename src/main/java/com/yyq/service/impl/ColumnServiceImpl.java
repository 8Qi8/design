package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.mapper.ArticleMapper;
import com.yyq.mapper.ColumnMapper;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.entity.Columns;
import com.yyq.service.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Columns> implements IColumnService {
    @Autowired
    private ArticleMapper articleMapper;
    /**
     * 根据用户id查询专栏
     * @param userId
     * @return
     */
    public List<Columns> listByUserId(Long userId) {
        QueryWrapper<Columns> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }
    /**
     * 根据专栏id列表查询文章数量
     * @param columnIds
     * @return
     */
    @Override
    public Map<Long, Long> getArticleCountsByColumnIds(List<Long> columnIds) {
        if (columnIds == null || columnIds.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, Long> resultMap = new HashMap<>();
        for (Long columnId : columnIds) {
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("column_id", columnId);
            Long count = articleMapper.selectCount(queryWrapper);
            resultMap.put(columnId, count);
        }
        return resultMap;
    }
}
