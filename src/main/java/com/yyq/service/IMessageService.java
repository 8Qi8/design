package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;

import java.util.List;

public interface IMessageService extends IService<Message>{
    void sendPrivateMessage(Message message);
    void sendCommentNotification(Long senderId, Long receiverId, String content);
    void sendLikeNotification(Long senderId, Long receiverId, String content);
    void sendFollowNotification(Long senderId, Long receiverId, String content);
    void sendSystemNotification(String content);

    List<Message> getMessagesByUser(Long userId);

    List<Message> getPrivateMessages(Long userId);

    List<ChatUserVO> getChatUserList(Long userId);

    List<Message> getMessagesByUserAndFriend(Long userId, Long friendId);
}
