package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.dto.CommentDTO;
import com.yyq.pojo.entity.Comment;
import com.yyq.pojo.vo.CommentVO;

import java.util.List;

public interface ICommentService extends IService<Comment> {
    void addComment(CommentDTO commentDTO);
    List<CommentVO> getCommentsByArticleId(Long articleId);

    void deleteComment(Long id);
}
