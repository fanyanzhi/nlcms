package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Shareinfo;
import cn.gov.nlc.pojo.ShareinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareinfoMapper {
    int countByExample(ShareinfoExample example);

    int deleteByExample(ShareinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shareinfo record);

    int insertSelective(Shareinfo record);

    List<Shareinfo> selectByExample(ShareinfoExample example);

    Shareinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Shareinfo record, @Param("example") ShareinfoExample example);

    int updateByExample(@Param("record") Shareinfo record, @Param("example") ShareinfoExample example);

    int updateByPrimaryKeySelective(Shareinfo record);

    int updateByPrimaryKey(Shareinfo record);
}