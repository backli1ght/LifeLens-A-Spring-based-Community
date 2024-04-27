package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param notation is used to specify the parameter name in the SQL statement
    // If only one parameter is used, and it is used in <if>, @Param is essential
    int selectDiscussPostRows (@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);
}
