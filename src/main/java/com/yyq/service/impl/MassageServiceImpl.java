package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyq.handler.MyWebSocketHandler;
import com.yyq.mapper.MessageMapper;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
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


    @Override
    public void sendPrivateMessage(Message message) {
        sendMessageAndPush(message.getSenderId(), message.getReceiverId(), message.getContent(), "PRIVATE");
    }

    @Override
    public void sendCommentNotification(Long senderId, Long receiverId, String content) {
        sendMessageAndPush(senderId, receiverId, content, "COMMENT");
    }

    @Override
    public void sendLikeNotification(Long senderId, Long receiverId, String content) {
        sendMessageAndPush(senderId, receiverId, content, "LIKE");
    }

    @Override
    public void sendFollowNotification(Long senderId, Long receiverId, String content) {
        sendMessageAndPush(senderId, receiverId, content, "FOLLOW");
    }

    @Override
    public void sendSystemNotification(String content) {
        // 此处模拟推送给所有人（可替换为具体查询）
        List<Long> allUserIds = List.of(1L, 2L, 3L); // 示例用户 ID
        for (Long userId : allUserIds) {
            sendMessageAndPush(0L, userId, content, "SYSTEM");
        }
    }

    @Override
    public List<Message> getMessagesByUser(Long userId) {
        return messageMapper.selectList(
                new QueryWrapper<Message>().eq("receiver_id", userId).orderByDesc("create_time")
        );
    }

    @Override
    public List<Message> getPrivateMessages(Long userId) {
        return messageMapper.selectPrivateMessagesByUserId(userId);
    }

    @Override
    public List<ChatUserVO> getChatUserList(Long userId) {
        return messageMapper.listChatUsers(userId);
    }

    @Override
    public List<Message> getMessagesByUserAndFriend(Long userId, Long friendId) {
       return messageMapper.selectPrivateMessagesByUserIdAndFriendId(userId, friendId);
    }
}
