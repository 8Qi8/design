package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<Message> selectPrivateMessagesByUserId(Long userId);

    List<ChatUserVO> listChatUsers(Long userId);
    @Select("select * from message where (sender_id = #{userId} and receiver_id = #{friendId}) or (sender_id = #{friendId} and receiver_id = #{userId})")
    List<Message> selectPrivateMessagesByUserIdAndFriendId(Long userId, Long friendId);
}
