package com.yyq.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.common.result.PageResult;
import com.yyq.pojo.entity.Label;

public interface ILabelService extends IService<Label> {

    PageResult getLabelPage(int current, int size);
}
