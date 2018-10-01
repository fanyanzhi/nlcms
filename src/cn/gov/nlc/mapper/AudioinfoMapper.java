package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Audioinfo;
import cn.gov.nlc.pojo.AudioinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AudioinfoMapper {
    int countByExample(AudioinfoExample example);

    int deleteByExample(AudioinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Audioinfo record);

    int insertSelective(Audioinfo record);

    List<Audioinfo> selectByExample(AudioinfoExample example);

    Audioinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Audioinfo record, @Param("example") AudioinfoExample example);

    int updateByExample(@Param("record") Audioinfo record, @Param("example") AudioinfoExample example);

    int updateByPrimaryKeySelective(Audioinfo record);

    int updateByPrimaryKey(Audioinfo record);
}