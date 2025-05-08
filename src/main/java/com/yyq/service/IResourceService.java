package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.Resource;
import com.yyq.pojo.vo.ResourceVO;

import java.util.List;

public interface IResourceService extends IService<Resource> {
    List<ResourceVO> getList();
    // 根据标题和描述搜索资源
    List<ResourceVO> getListByTitleAndDescription(String searchKey);
}
