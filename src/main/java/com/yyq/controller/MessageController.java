package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
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

    @PostMapping("/privates")
    public Result<String> sendPrivate(@RequestBody Message message) {
        messageService.sendPrivateMessage(message);
        return Result.success("发送成功");
    }

    @PostMapping("/comments")
    public Result<String> sendComment(@RequestParam Long senderId,
                         @RequestParam Long receiverId,
                         @RequestParam String content) {
        messageService.sendCommentNotification(senderId, receiverId, content);
        return Result.success("评论提醒已发送");
    }

    @PostMapping("/likes")
    public Result<String> sendLike(@RequestParam Long senderId,
                      @RequestParam Long receiverId,
                      @RequestParam String content) {
        messageService.sendLikeNotification(senderId, receiverId, content);
        return Result.success("点赞提醒已发送");
    }

    @PostMapping("/follows")
    public Result<String> sendFollow(@RequestParam Long senderId,
                        @RequestParam Long receiverId,
                        @RequestParam String content) {
        messageService.sendFollowNotification(senderId, receiverId, content);
        return Result.success("新增粉丝提醒已发送");
    }

    @PostMapping("/systems")
    public Result sendSystem(@RequestParam String content) {
        messageService.sendSystemNotification(content);
        return Result.success("系统通知已广播");
    }

    @GetMapping("/list")
    public Result<List<Message>> getMessages(@RequestParam Long userId) {
        return Result.success(messageService.getMessagesByUser(userId));
    }
    /**
     * 获取用户收到的私信列表
     */
    @GetMapping("/private/{userId}")
    public Result<List<Message>> getPrivateMessages(@PathVariable Long userId) {
        List<Message> messages = messageService.getPrivateMessages(userId);
        return Result.success(messages);
    }
    /**
     * 获取用户收到的私信列表
     */
    @GetMapping("/chatUsers/{userId}")

    public Result<List<ChatUserVO>> getChatUserList(@PathVariable Long userId) {
        List<ChatUserVO> chatUserList = messageService.getChatUserList(userId);
        return Result.success(chatUserList);
    }
    /**
     * 获取与某用户的聊天记录
     *
     */
    @GetMapping("/chat/{userId}/{friendId}")
    public Result<List<Message>> getChatMessages(@PathVariable Long userId, @PathVariable Long friendId) {
        List<Message> messages = messageService.getMessagesByUserAndFriend(userId, friendId);
        return Result.success(messages);
    }
}
