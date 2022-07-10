package com.example.paymentdemo.mapper;

import com.example.paymentdemo.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * order mapper
 */
public interface VideoOrderMapper {

    /**
     * save order
     * @param videoOrder
     * @return
     */
    @Insert("INSERT INTO `video_order` (`openid`, `out_trade_no`, `state`, `create_time`," +
            " `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, `video_title`," +
            " `video_img`, `user_id`, `ip`, `del`)" +
            "VALUES" +
            "(#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee}," +
            "#{nickname},#{headImg},#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insert(VideoOrder videoOrder);

    /**
     * select order where is not delete by given id
     * given annotation when > 1 param
     * @param id
     * @return
     */
    @Select("select * from video_order where id=#{order_id} and del=0")
    VideoOrder findById(@Param("order_id") int id);

    /**
     * find order where is not delete by out trade no
     * @param outTradeNo
     * @return
     */
    @Select("select * from video_order where out_trade_no=#{out_trade_no} and del=0")
    VideoOrder findByOutTradeNo(@Param("out_trade_no") String outTradeNo);

    /**
     * delete given order
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del=1 where id=#{id} and user_id =#{userId}")
    int del(@Param("id") int id, @Param("userId") int userId);

    /**
     * select video by given userid
     * @param userId
     * @return
     */
    @Select("select * from video_order where user_id =#{userId}")
    List<VideoOrder> findMyOrderList(int userId);

    /**
     * update video order by given out trade number
     * @param videoOrder
     * @return
     */
    @Update("update video_order set state=#{state}, notify_time=#{notifyTime}, openid=#{openid}" +
            " where out_trade_no=#{outTradeNo} and state=0 and del=0")
    int updateVideoOderByOutTradeNo(VideoOrder videoOrder);
}
