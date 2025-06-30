package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
import com.yyq.pojo.vo.CommentNotificationVO;
import com.yyq.pojo.vo.FollowNotificationVO;
import com.yyq.pojo.vo.LikeNotificationVO;
import com.yyq.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private IMessageService messageService;
    /**
     * 发送私信
     */
    @PostMapping("/privates")
    public Result<String> sendPrivate(@RequestBody Message message) {
        messageService.sendPrivateMessage(message);
        return Result.success("发送成功");
    }
    /**
     * 发送评论提醒
     */
    @PostMapping("/comments")
    public Result<String> sendComment(@RequestParam Long senderId,
                         @RequestParam Long receiverId,
                         @RequestParam String content) {
        messageService.sendCommentNotification(senderId, receiverId, content);
        return Result.success("评论提醒已发送");
    }
    /**
     * 发送点赞提醒
     */
    @PostMapping("/likes")
    public Result<String> sendLike(@RequestParam Long senderId,
                      @RequestParam Long receiverId,@RequestParam String content) {
        messageService.sendLikeNotification(senderId, receiverId,content);
        return Result.success("点赞提醒已发送");
    }
    /**
     * 发送新增粉丝提醒
     */
    @PostMapping("/follows")
    public Result<String> sendFollow(@RequestParam Long senderId,
                        @RequestParam Long receiverId,
                        @RequestParam String content) {
        messageService.sendFollowNotification(senderId, receiverId, content);
        return Result.success("新增粉丝提醒已发送");
    }
    /**
     * 发布系统通知
     */
    @PostMapping("/system/{adminId}")
    public Result<String> sendSystem(@RequestParam String content, @PathVariable Long adminId) {
        messageService.sendSystemNotification(content, adminId);
        return Result.success("系统通知已发布");
    }


    /**
     * 获取用户收到的私信用户列表
     */
    @GetMapping("/chatUsers/{userId}")

    public Result<List<ChatUserVO>> getChatUserList(@PathVariable Long userId) {
        List<ChatUserVO> chatUserList = messageService.getChatUserList(userId);
        return Result.success(chatUserList);
    }
    /**
     * 获取与某用户的聊天记录
     */
    @GetMapping("/chat/{userId}/{friendId}")
    public Result<List<Message>> getChatMessages(@PathVariable Long userId, @PathVariable Long friendId) {
        List<Message> messages = messageService.getMessagesByUserAndFriend(userId, friendId);
        return Result.success(messages);
    }
    /**
     * 获取点赞提醒
     */
    @GetMapping("/like/{userId}")
    public Result<List<LikeNotificationVO>> getLikeMessages(@PathVariable Long userId) {
        List<LikeNotificationVO> list = messageService.getLikeNotifications(userId);
        return Result.success(list);
    }
    /**
     * 获取关注提醒
     */
    @GetMapping("/fan/{userId}")
    public Result<List<FollowNotificationVO>> getFollowMessages(@PathVariable Long userId) {
        List<FollowNotificationVO> list = messageService.getFollowNotifications(userId);
        return Result.success(list);
    }
    /**
     * 获取评论提醒
     */
    @GetMapping("/comment/{userId}")
    public Result<List<CommentNotificationVO>> getCommentMessages(@PathVariable Long userId) {
        List<CommentNotificationVO> list = messageService.getCommentNotificationsByUserId(userId);
        return Result.success(list);
    }
    /**
     * 获取系统通知
     */
    @GetMapping("/system/{userId}")
    public Result<List<Message>> getSystemMessages(@PathVariable Long userId) {
        List<Message> list = messageService.getSystemMessages(userId);
        return Result.success(list);
    }


}
