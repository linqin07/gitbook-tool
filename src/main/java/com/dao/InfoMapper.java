package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Info;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linqin
 * @since 2019-05-31
 */
public interface InfoMapper extends BaseMapper<Info> {

    @Select("SELECT * FROM info WHERE pic_url = #{picUrl}")
    Info selectByPicUrl(String picUrl);

    @Select("SELECT * FROM info WHERE pic_url_md = #{picUrlMd}")
    Info selectByPicUrlMd(String picUrlMd);

    @Select("SELECT * FROM info WHERE pic_local_md = #{picLocalMd}")
    Info selectByPicLocalMd(String picLocalMd);

}
