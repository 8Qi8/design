package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.mapper.ArticleMapper;
import com.yyq.mapper.CommentMapper;
import com.yyq.mapper.MessageMapper;
import com.yyq.mapper.UserMapper;
import com.yyq.pojo.dto.CommentDTO;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.entity.Comment;
import com.yyq.pojo.vo.CommentVO;
import com.yyq.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 添加评论
     * @param commentDTO
     * @return
     */
    @Override
    @Transactional
    public void addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.insert(comment);
        //文章评论数+1
        articleMapper.update(new UpdateWrapper<Article>()
                .setSql("comment_count = comment_count + 1")
                .eq("id", commentDTO.getArticleId())

        );
        //文章热度+1
        articleMapper.update(new UpdateWrapper<Article>()
                .setSql("heat = heat + 1")
                .eq("id", commentDTO.getArticleId())
        );

    }
    /**
     * 根据文章id获取评论
     * @param articleId
     * @return
     */
    @Override
    public List<CommentVO> getCommentsByArticleId(Long articleId) {
        // 1. 查询文章的所有评论
        List<Comment> commentEntities = commentMapper.selectList(
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getArticleId, articleId)
                        .orderByAsc(Comment::getCreateTime)
        );

        // 2. 转换为 CommentVO，并组织树结构
        List<CommentVO> rootComments = new ArrayList<>();
        Map<Long, CommentVO> commentMap = new HashMap<>();

        for (Comment entity : commentEntities) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(entity, vo);
            vo.setUserName(userMapper.findUserNameById(entity.getUserId()));
            vo.setUserAvatar(userMapper.findUserAvatarById(entity.getUserId()));
            if (entity.getTargetUserId() != null) { //是否为子评论
                vo.setTargetUserName(userMapper.findUserNameById(entity.getTargetUserId()));

            }
            commentMap.put(vo.getId(), vo);

            if (vo.getParentId() == 0) {
                rootComments.add(vo); // 一级评论
            } else {
                CommentVO parent = commentMap.get(vo.getParentId());
                if (parent != null) {
                    parent.getChildren().add(vo); // 添加子评论
                }
            }
        }
        return rootComments;
    }
    /**
     * 删除评论
     * @param id
     */
    @Override
    @Transactional
    public void deleteComment(Long id) {
        // 获取被删除评论的所属文章ID
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        Long articleId = comment.getArticleId();
        // 删除该评论及其子评论，并获取删除数量
        int deletedCount = commentMapper.delete(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getId, id)
                .or()
                .eq(Comment::getParentId, id));

        // 更新文章评论数
        articleMapper.update(
                new UpdateWrapper<Article>()
                        .setSql("comment_count = comment_count - " + deletedCount)
                        .eq("id", articleId)
        );
    }


}
