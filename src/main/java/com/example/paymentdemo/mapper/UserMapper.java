package com.example.paymentdemo.mapper;

import com.example.paymentdemo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    /**
     * find user by id
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") int userId);

    /**
     * find user by open id
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User findByOpenid(@Param("openid") String openid);

    /**
     * save user
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user` ( `openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`)" +
            "VALUES" +
            "(#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(User user);
}
