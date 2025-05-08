package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyq.handler.MyWebSocketHandler;
import com.yyq.mapper.MessageMapper;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
import com.yyq.pojo.vo.CommentNotificationVO;
import com.yyq.pojo.vo.FollowNotificationVO;
import com.yyq.pojo.vo.LikeNotificationVO;
import com.yyq.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MassageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;
    /**
     * 发送消息并推送给接收者
     *
     * @param senderId   消息发送者的 ID
     * @param receiverId 消息接收者的 ID
     * @param content    消息内容
     * @param type       消息类型
     */
    private void sendMessageAndPush(Long senderId, Long receiverId, String content, String type) {
        Message msg = new Message();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setContent(content);
        msg.setType(type);
        msg.setIsRead(0);
        msg.setCreateTime(LocalDateTime.now());
        messageMapper.insert(msg);

        // 构造 JSON 推送
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("senderId", senderId);
        map.put("content", content);
        map.put("timestamp", msg.getCreateTime().toString());

        try {
            String json = new ObjectMapper().writeValueAsString(map);
            if ("SYSTEM".equals(type)) {
                MyWebSocketHandler.sendToAll(json);
            } else {
                MyWebSocketHandler.sendToUser(receiverId, json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送私聊消息
     *
     * @param message 私聊消息对象
     */
    @Override
    public void sendPrivateMessage(Message message) {
        sendMessageAndPush(message.getSenderId(), message.getReceiverId(), message.getContent(), "PRIVATE");
    }
    /**
     * 发送评论通知
     *
     * @param senderId   发送者 ID
     * @param receiverId 接收者 ID
     * @param content    通知内容
     */
    @Override
    public void sendCommentNotification(Long senderId, Long receiverId, String content) {
        sendMessageAndPush(senderId, receiverId, content, "COMMENT");
    }
    /**
     * 发送点赞通知
     *
     * @param senderId   发送者 ID
     * @param receiverId 接收者 ID
     */
    @Override
    public void sendLikeNotification(Long senderId, Long receiverId,String content) {

        sendMessageAndPush(senderId, receiverId,content,"LIKE");
    }
    /**
     * 发送关注通知
     *
     * @param senderId   发送者 ID
     * @param receiverId 接收者 ID
     * @param content    通知内容
     */
    @Override
    public void sendFollowNotification(Long senderId, Long receiverId, String content) {
        sendMessageAndPush(senderId, receiverId, content, "FOLLOW");
    }
    /**
     * 发送系统通知
     *
     * @param content 通知内容
     */
    @Override
    public void sendSystemNotification(String content) {
        // 此处模拟推送给所有人（可替换为具体查询）
        List<Long> allUserIds = List.of(1L, 2L, 3L); // 示例用户 ID
        for (Long userId : allUserIds) {
            sendMessageAndPush(0L, userId, content, "SYSTEM");
        }
    }

    /**
     * 获取聊天用户列表
     * @param userId
     * @return
     */
    @Override
    public List<ChatUserVO> getChatUserList(Long userId) {
        return messageMapper.listChatUsers(userId);
    }
    /**
     * 根据用户 ID 和好友 ID 获取聊天记录
     * @param userId
     * @param friendId
     * @return
     */
    @Override
    public List<Message> getMessagesByUserAndFriend(Long userId, Long friendId) {
       return messageMapper.selectPrivateMessagesByUserIdAndFriendId(userId, friendId);
    }
    /**
     * 根据用户 ID 获取点赞消息
     * @param userId
     * @return
     */
    @Override
    public List<Message> getLikeMessagesByUserId(Long userId) {
        return messageMapper.selectLikeMessagesByUserId(userId);
    }
    /**
     * 根据用户 ID 获取点赞通知
     * @param userId
     * @return
     */
    @Override
    public List<LikeNotificationVO> getLikeNotifications(Long userId) {
        return messageMapper.getLikeNotifications(userId);
    }
    /**
     * 根据用户 ID 获取关注通知
     * @param userId
     * @return
     */
    @Override
    public List<FollowNotificationVO> getFollowNotifications(Long userId) {
        return messageMapper.getFollowNotifications(userId);
    }

    @Override
    public List<CommentNotificationVO> getCommentNotificationsByUserId(Long userId) {
        return messageMapper.selectCommentNotificationsByUserId(userId);
    }
}
