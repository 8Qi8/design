package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyq.handler.MyWebSocketHandler;
import com.yyq.mapper.MessageMapper;
import com.yyq.mapper.UserMapper;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.vo.ChatUserVO;
import com.yyq.pojo.vo.CommentNotificationVO;
import com.yyq.pojo.vo.FollowNotificationVO;
import com.yyq.pojo.vo.LikeNotificationVO;
import com.yyq.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MassageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
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
    public void sendSystemNotification(String content, Long adminId) {
        // 根据角色编号查询所有的学生和教师的id
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId)
                .and(w -> w.eq(User::getRole, 1).or().eq(User::getRole, 3));
        List<User> users = userMapper.selectList(wrapper);
        // 批量发送消息
        List<Message> messages = new ArrayList<>();

        for (User user : users) {
            Message message = new Message();
            message.setSenderId(adminId);           // 系统管理员ID
            message.setReceiverId(user.getId());    // 接收人ID
            message.setType("SYSTEM");       // 消息类型，自定义枚举或字符串
            message.setContent(content);            // 通知内容
            message.setCreateTime(LocalDateTime.now());
            message.setIsRead(0);               // 默认未读
            messages.add(message);
        }

        // 批量插入通知消息
        messageMapper.insertBatchSomeColumn(messages);  // 你可以改成你项目实际的批量插入方法

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
    /**
     * 获取系统消息
     * @return
     */
    @Override
    public List<Message> getSystemMessages(Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getType, "SYSTEM").and(w -> w.eq(Message::getReceiverId, userId));
        return messageMapper.selectList(wrapper);
    }
}
