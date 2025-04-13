package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.common.result.PageResult;
import com.yyq.pojo.dto.ArticleAddDTO;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.vo.ArticleVO;

import java.util.List;

public interface IArticleService extends IService<Article> {

    void save(ArticleAddDTO articleAddDTO);

    List<ArticleVO> getDraft(Long id);

    List<ArticleVO> getPublished(Long id);

    void updateId(ArticleAddDTO articleAddDTO);

    void deleteBatchByIds(List<Long> ids);

    ArticleVO getOneWithLabel(Long id);

    PageResult getArticlePage(int currentSize, int pageSize);

    void increaseViews(Long id);

    void likeArticle(Long id, Long userId);

    void unlikeArticle(Long id, Long userId);

    List<ArticleVO> getByColumnId(Long columnId);

    boolean cancelBatchCollection(List<Long> articleIds);

    boolean batchCollection(List<Long> articleIds, Long columnId);

    List<ArticleVO> getUncollectedArticles(Long columnId, Long userId);

    List<ArticleVO> getList();

    List<ArticleVO> getByLabelId(Long labelId);
}
