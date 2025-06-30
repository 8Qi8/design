package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
import com.yyq.pojo.vo.CommentNotificationVO;
import com.yyq.pojo.vo.FollowNotificationVO;
import com.yyq.pojo.vo.LikeNotificationVO;

import java.util.List;

public interface IMessageService extends IService<Message>{
    void sendPrivateMessage(Message message);
    void sendCommentNotification(Long senderId, Long receiverId, String content);
    void sendLikeNotification(Long senderId, Long receiverId,String content);
    void sendFollowNotification(Long senderId, Long receiverId, String content);
    void sendSystemNotification(String content, Long adminId);


    List<ChatUserVO> getChatUserList(Long userId);

    List<Message> getMessagesByUserAndFriend(Long userId, Long friendId);

    List<Message> getLikeMessagesByUserId(Long userId);

    List<LikeNotificationVO> getLikeNotifications(Long userId);

    List<FollowNotificationVO> getFollowNotifications(Long userId);

    List<CommentNotificationVO> getCommentNotificationsByUserId(Long userId);

    List<Message> getSystemMessages(Long userId);
}
