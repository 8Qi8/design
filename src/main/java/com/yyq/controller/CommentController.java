package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.dto.CommentDTO;
import com.yyq.pojo.entity.Comment;
import com.yyq.pojo.vo.CommentVO;
import com.yyq.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    /**
     * 发表评论
     * @param commentDTO 评论实体（JSON格式）
     * @return 操作结果
     */
    @PostMapping
    public Result<String> addComment(@RequestBody CommentDTO commentDTO) {
        log.info("发表评论：{}",commentDTO);
        commentService.addComment(commentDTO);
        return Result.success("发表评论成功！");
    }
    /**
     * 根据文章id获取评论列表
     * @param articleId 文章id
     * @return 评论列表
     */
    @GetMapping("/list/{articleId}")
    public Result<List<CommentVO>> getComments(@PathVariable Long articleId) {
        log.info("获取评论列表：{}",articleId);
        List<CommentVO> comments = commentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }
    /**
     * 删除评论
     *
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteComment(@PathVariable Long id) {
        log.info("删除评论：{}",id);
        commentService.deleteComment(id);
        return Result.success("删除评论成功！");
    }
}
