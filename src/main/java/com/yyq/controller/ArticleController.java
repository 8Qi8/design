package com.yyq.controller;

import com.yyq.common.result.PageResult;
import com.yyq.common.result.Result;
import com.yyq.pojo.dto.ArticleAddDTO;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.vo.ArticleVO;
import com.yyq.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    /**
     * 添加文章
     */
    @PostMapping
    public Result<String> add(@RequestBody ArticleAddDTO articleAddDTO) {
        log.info("添加文章：{}",articleAddDTO);
        articleService.save(articleAddDTO);
        return Result.success("添加成功");
    }
    /**
     * 获取所有已发表文章
     */
    @GetMapping("/list")
    public Result<List<ArticleVO>> getAll() {
        log.info("获取所有已发表文章");
        List<ArticleVO> list = articleService.getList();
        return Result.success(list);
    }

    /**
     * 根据用户id获取草稿箱
     */
    @GetMapping("/{id}/draft")
    public Result<List<ArticleVO>> getDraft(@PathVariable Long id) {
        log.info("获取草稿箱：{}",id);
        List<ArticleVO> list = articleService.getDraft(id);
        return Result.success(list);
    }
    /**
     * 根据用户id获取已发表文章
     */
    @GetMapping("/{id}/published")
    public Result<List<ArticleVO>> getPublished(@PathVariable Long id) {
        log.info("获取已发表文章：{}",id);
        List<ArticleVO> list = articleService.getPublished(id);
        return Result.success(list);
    }
    /**
     * 根据id获取文章
     */
    @GetMapping("/{id}")
    public Result<ArticleVO> getById(@PathVariable Long id) {
        log.info("获取文章：{}",id);
        ArticleVO article = articleService.getOneWithLabel(id);
        return Result.success(article);
    }
    /**
     * 编辑文章
     */
    @PutMapping
    public Result<String> update(@RequestBody ArticleAddDTO articleAddDTO) {
        log.info("编辑文章：{}",articleAddDTO);
        articleService.updateId(articleAddDTO);
        return Result.success("编辑成功");
    }

    /**
     * 批量删除文章
     */
    @DeleteMapping
    public Result<String> deleteBatch(@RequestParam List<Long> ids) {
        log.info("批量删除文章：{}",ids);
        articleService.deleteBatchByIds(ids);
        return Result.success("批量删除成功");
    }
    /**
     * 分页查询已发布文章
     */
    @GetMapping("/{currentSize}/{pageSize}")
    public PageResult getArticlePage(@PathVariable int currentSize, @PathVariable int pageSize) {
        log.info("分页查询文章：{}",currentSize);
        PageResult pageResult = articleService.getArticlePage(currentSize, pageSize);
        return pageResult;
    }
    /**
     * 增加某篇文章阅读数，热度+1
     */
    @PutMapping("/{id}/read")
    public Result<String> read(@PathVariable Long id) {
        log.info("增加某篇文章阅读数：{}",id);
        articleService.increaseViews(id);
        return Result.success("增加成功");
    }

    /**
     * 为某篇文章点赞，文章热度+3
     */
    @PutMapping("/{id}/{userId}/like")
    public Result<String> like(@PathVariable Long id, @PathVariable Long userId) {
        log.info("用户{}为文章{}点赞", userId, id);
        articleService.likeArticle(id, userId);
        return Result.success("点赞成功");
    }

    /**
     * 为某篇文章取消点赞
     */
    @PutMapping("/{id}/{userId}/unlike")
    public Result<String> unlike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("用户{}为文章{}取消点赞", userId, id);
        articleService.unlikeArticle(id, userId);
        return Result.success("取消点赞成功");
    }

    /**
     * 查询某用户的粉丝、文章被阅读总数、获赞总数
     */
    //@GetMapping("/{id}/stats")


    /**
     *查询某一专栏下的文章
     */
    @GetMapping("/column/{columnId}")
    public Result<List<ArticleVO>> getByColumnId(@PathVariable Long columnId) {
        log.info("查询某一专栏下的文章：{}",columnId);
        List<ArticleVO> list = articleService.getByColumnId(columnId);
        return Result.success(list);
    }
    /**
     * 批量添加至某专栏
     */
    @PutMapping("/collection")
    public Result<String> batchCollection(@RequestParam List<Long> articleIds, @RequestParam Long columnId) {
        log.info("批量添加至某专栏: {}", articleIds);
        // 验证是否包含有效ID
        if (articleIds == null || articleIds.isEmpty()) {
            return Result.error("请选择需要收录的文章");
        }
        // 执行批量添加操作
        boolean success = articleService.batchCollection(articleIds, columnId);
        return success ?
                Result.success("批量添加成功") :
               Result.error("操作失败，请检查文章状态");
    }
    /**
     * 批量取消收录某片文章
     */
    @PutMapping("/cancelCollection")
    public Result<String> cancelBatchCollection(@RequestParam List<Long> articleIds) {
        log.info("批量取消收录文章: {}", articleIds);
        // 验证是否包含有效ID
        if (articleIds == null || articleIds.isEmpty()) {
            return Result.error("请选择需要取消收录的文章");
        }
        // 执行批量取消收录操作
        boolean success = articleService.cancelBatchCollection(articleIds);
        return success ?
                Result.success("批量取消收录成功") :
                Result.error("操作失败，请检查文章状态");
    }
    /**
     * 获取当前用户未收录至当前专栏的所有文章
     */
    @GetMapping("/{columnId}/uncollected")
    public Result<List<ArticleVO>> getUncollectedArticles(@PathVariable Long columnId, @RequestParam Long userId) {
        log.info("获取当前用户: {} 未收录至当前专栏: {}的所有文章", userId, columnId);
        List<ArticleVO> list = articleService.getUncollectedArticles(columnId, userId);
        return Result.success(list);
    }
    /**
     * 批量移动至其它专栏
     */
    @PutMapping("/move")
    public Result<String> moveToOtherColumn(@RequestParam List<Long> articleIds, @RequestParam Long columnId) {
        log.info("批量移动至其它专栏: {}", articleIds);
        return articleService.batchCollection(articleIds, columnId) ?
                Result.success("批量移动成功") :
                Result.error("操作失败，请检查文章状态");
    }
}
