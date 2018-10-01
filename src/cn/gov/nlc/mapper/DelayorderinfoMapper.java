package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Delayorderinfo;
import cn.gov.nlc.pojo.DelayorderinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DelayorderinfoMapper {
    int countByExample(DelayorderinfoExample example);

    int deleteByExample(DelayorderinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Delayorderinfo record);

    int insertSelective(Delayorderinfo record);

    List<Delayorderinfo> selectByExample(DelayorderinfoExample example);

    Delayorderinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Delayorderinfo record, @Param("example") DelayorderinfoExample example);

    int updateByExample(@Param("record") Delayorderinfo record, @Param("example") DelayorderinfoExample example);

    int updateByPrimaryKeySelective(Delayorderinfo record);

    int updateByPrimaryKey(Delayorderinfo record);
}