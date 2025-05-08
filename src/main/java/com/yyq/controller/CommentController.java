package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.dto.CommentDTO;
import com.yyq.pojo.entity.Comment;
import com.yyq.pojo.vo.CommentVO;
import com.yyq.service.IArticleService;
import com.yyq.service.ICommentService;
import com.yyq.service.IMessageService;
import com.yyq.service.IUserService;
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
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;
    /**
     * 发表评论
     * @param commentDTO 评论实体（JSON格式）
     * @return 操作结果
     */
    @PostMapping
    public Result<String> addComment(@RequestBody CommentDTO commentDTO) {
        log.info("发表评论：{}",commentDTO);
        commentService.addComment(commentDTO);
        //是否发送评论通知
        if (commentDTO.getParentId() == 0) { //根评论给文章作者发送通知
            String username = userService.getById(commentDTO.getUserId()).getUsername();
            String articleTitle = articleService.getById(commentDTO.getArticleId()).getTitle();
            String content = "用户【" + username + "】评论了你的文章《" + articleTitle + "》："+commentDTO.getContent();
            messageService.sendCommentNotification(commentDTO.getUserId(),commentDTO.getTargetUserId(),content);
        }
        else { //不是根评论
            String username = userService.getById(commentDTO.getUserId()).getUsername();
            String articleTitle = articleService.getById(commentDTO.getArticleId()).getTitle();
            String content = "用户【" + username + "】回复了你在文章：《"+articleTitle+"》下的评论:" + commentDTO.getContent() ;
            messageService.sendCommentNotification(commentDTO.getUserId(),commentDTO.getTargetUserId(),content);

        }
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
