package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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

    @Select("SELECT * FROM info WHERE pic_local__path = #{picLocalPath}")
    Info selectByPicLocal(String picLocalPath);

    @Insert("INSERT INTO info(pic_name, pic_local_path, pic_url, `sha`) " +
            "VALUES (#{info.picName}, #{info.picLocalPath}, #{info.picUrl}, #{info.sha}) " +
            "ON DUPLICATE KEY UPDATE pic_local_path= #{info.picLocalPath}, pic_url=#{info.picUrl}, `sha`=#{info.sha}")
    int insertOrUpdate(@Param("info") Info info);

}
