package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Resource;
import com.yyq.pojo.vo.ResourceVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    List<ResourceVO> getListWithUser();

    List<ResourceVO> getListByTitleAndDescription(String searchKey);
}
