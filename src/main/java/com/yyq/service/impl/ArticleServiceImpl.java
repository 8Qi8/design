package com.yyq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyq.common.result.PageResult;
import com.yyq.mapper.ArticleLabelMapper;
import com.yyq.mapper.ArticleLikeMapper;
import com.yyq.mapper.ArticleMapper;
import com.yyq.mapper.LabelMapper;
import com.yyq.pojo.dto.ArticleAddDTO;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.entity.ArticleLabel;
import com.yyq.pojo.entity.ArticleLike;
import com.yyq.pojo.entity.Label;
import com.yyq.pojo.vo.ArticleVO;
import com.yyq.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private ArticleLabelMapper articleLabelMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleLikeMapper articleLikeMapper;

    /**
     * 保存文章
     *
     * @param articleAddDTO
     */
    @Override
    @Transactional
    public void save(ArticleAddDTO articleAddDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleAddDTO, article);
        // 2. 设置业务字段
        article.setUpdateId(articleAddDTO.getUserId());
        article.setViews(0);  // 初始浏览量
        article.setHeat(0);  // 初始热度
        article.setIsTop(0);
        article.setLikeCount(0);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        // 4. 保存到数据库
        baseMapper.insert(article);
        // 5. 保存文章和标签的关联关系
        List<Long> labelIds = articleAddDTO.getLabelIds();
        //批量插入
        List<ArticleLabel> labels = new ArrayList<>();
        for (Long labelId : labelIds) {
            ArticleLabel articleLabel = new ArticleLabel();
            articleLabel.setArticleId(article.getId());
            articleLabel.setLabelId(labelId);
            labels.add(articleLabel);
        }
        articleLabelMapper.insertBatch(labels);
        // 更新标签的热度
        if (labelIds != null && labelIds.size() > 0) {
            log.info("更新标签的热度");
            labelMapper.updateHeatBatch(labelIds, 5);
        }
    }


    /**
     * 获取草稿箱中的文章
     *
     * @param id
     * @return
     */
    @Override
    public List<ArticleVO> getDraft(Long id) {
        List<Article> articles = lambdaQuery()
                .eq(Article::getUserId, id)
                .eq(Article::getStatus, "draft")
                .list();
        List<ArticleVO> voList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            // 查询关联标签ID列表
            List<Long> labelIds = articleLabelMapper.selectLabelIdsByArticleId(article.getId());
            vo.setLabelIds(labelIds);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 获取已发布文章
     *
     * @param id
     * @return
     */
    @Override
    public List<ArticleVO> getPublished(Long id) {
        List<Article> articles = lambdaQuery()
                .eq(Article::getUserId, id)
                .eq(Article::getStatus, "published")
                .list();
        List<ArticleVO> voList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            // 查询关联标签ID列表
            List<Long> labelIds = articleLabelMapper.selectLabelIdsByArticleId(article.getId());
            vo.setLabelIds(labelIds);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 更新文章
     *
     * @param articleAddDTO
     */
    @Override
    @Transactional
    public void updateId(ArticleAddDTO articleAddDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleAddDTO, article);
        article.setUpdateId(articleAddDTO.getUserId());
        article.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(article);
        // 删除文章和标签的关联关系
        articleLabelMapper.deleteBatch(Collections.singletonList(article.getId()));
        //保存新的文章和标签的关联关系
        List<Long> labelIds = articleAddDTO.getLabelIds();
        //批量插入
        List<ArticleLabel> labels = new ArrayList<>();
        for (Long labelId : labelIds) {
            ArticleLabel articleLabel = new ArticleLabel();
            articleLabel.setArticleId(article.getId());
            articleLabel.setLabelId(labelId);
            labels.add(articleLabel);
        }
        articleLabelMapper.insertBatch(labels);
        // 更新标签的热度
        if (labelIds != null && labelIds.size() > 0) {
            log.info("更新标签的热度");
            labelMapper.updateHeatBatch(labelIds, 5);
        }
    }

    /**
     * 批量删除文章
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatchByIds(List<Long> ids) {
        // 1. 删除文章
        baseMapper.deleteBatchIds(ids);
        // 2. 删除文章和标签的关联关系
        articleLabelMapper.deleteBatch(ids);
    }

    /**
     * 根据id获取文章
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVO getOneWithLabel(Long id) {
        Article article = baseMapper.selectById(id);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        // 2. 查询关联标签ID列表
        List<Long> labelIds = articleLabelMapper.selectLabelIdsByArticleId(id);
        articleVO.setLabelIds(labelIds);
        return articleVO;
    }

    /**
     * 分页查询文章
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public PageResult getArticlePage(int current, int size) {
        Page<Article> page = new Page<>(current, size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        //查询已发布文章
        queryWrapper.eq("status", "published");
        Page<Article> labelPage = articleMapper.selectPage(page, queryWrapper);
        return new PageResult(labelPage.getTotal(), labelPage.getRecords());
    }

    /**
     * 根据id增加浏览量+1
     *
     * @param id
     */
    @Override
    public void increaseViews(Long id) {
        baseMapper.update(null,
                new UpdateWrapper<Article>()
                        .setSql("views = views + 1")
                        .eq("id", id)
        );
    }

    /**
     * 根据id增加点赞量+1
     *
     * @param id
     */
    @Override
    @Transactional
    public void likeArticle(Long id, Long userId) {
        // 1. 点赞数+1
        baseMapper.update(null,
                new UpdateWrapper<Article>()
                        .setSql("like_count = like_count + 1")
                        .eq("id", id)
        );

        // 2. 插入点赞关系记录
        ArticleLike articleLike = new ArticleLike();
        articleLike.setUserId(userId);
        articleLike.setArticleId(id);
        articleLike.setCreateTime(LocalDateTime.now());
        articleLikeMapper.insert(articleLike);
    }
    //取消点赞
    @Override
    @Transactional
    public void unlikeArticle(Long id, Long userId) {
        // 1. 删除点赞关系记录
        articleLikeMapper.delete(new QueryWrapper<ArticleLike>()
                .eq("article_id", id)
                .eq("user_id", userId)
        );
        // 2. 点赞数-1
        baseMapper.update(null,
                new UpdateWrapper<Article>()
                        .setSql("like_count = like_count - 1")
                        .eq("id", id)
        );
    }


}
