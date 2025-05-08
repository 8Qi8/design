package com.yyq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.mapper.ResourceMapper;
import com.yyq.pojo.entity.Resource;
import com.yyq.pojo.vo.ResourceVO;
import com.yyq.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    //  获取所有资源
    @Override
    public List<ResourceVO> getList() {
        return resourceMapper.getListWithUser();
    }
    //  条件查询资源
    @Override
    public List<ResourceVO> getListByTitleAndDescription(String searchKey) {
        return resourceMapper.getListByTitleAndDescription(searchKey);
    }
}
