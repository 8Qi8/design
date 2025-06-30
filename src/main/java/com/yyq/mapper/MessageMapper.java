package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.vo.ChatUserVO;
import com.yyq.pojo.vo.CommentNotificationVO;
import com.yyq.pojo.vo.FollowNotificationVO;
import com.yyq.pojo.vo.LikeNotificationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    List<ChatUserVO> listChatUsers(Long userId);

    @Select("SELECT * FROM message " +
            "WHERE ((sender_id = #{userId} AND receiver_id = #{friendId}) " +
            "OR (sender_id = #{friendId} AND receiver_id = #{userId})) " +
            "AND type = 'PRIVATE'")
    List<Message> selectPrivateMessagesByUserIdAndFriendId(Long userId, Long friendId);

    @Select("select * from message where receiver_id = #{userId} and type = 'LIKE' order by create_time desc")
    List<Message> selectLikeMessagesByUserId(Long userId);

    List<LikeNotificationVO> getLikeNotifications(@Param("userId") Long userId);

    List<FollowNotificationVO> getFollowNotifications(@Param("userId") Long userId);

    List<CommentNotificationVO> selectCommentNotificationsByUserId(@Param("userId") Long userId);

    void insertBatchSomeColumn(List<Message> messages);
}
