package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.common.result.PageResult;
import com.yyq.mapper.LabelMapper;
import com.yyq.pojo.entity.Label;
import com.yyq.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Autowired
    private LabelMapper labelMapper;

    public PageResult getLabelPage(int current, int size) {
        Page<Label> page = new Page<>(current, size);
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        Page<Label> labelPage = labelMapper.selectPage(page, queryWrapper);
        return new PageResult(labelPage.getTotal(), labelPage.getRecords());
    }
}
