package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
// 假设存在LabelMapper接口
public interface LabelMapper extends BaseMapper<Label> {
    int updateHeatBatch(@Param("labelIds") List<Long> labelIds, @Param("increment") int increment);
}
