package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Appstart;
import cn.gov.nlc.pojo.AppstartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppstartMapper {
    int countByExample(AppstartExample example);

    int deleteByExample(AppstartExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appstart record);

    int insertSelective(Appstart record);

    List<Appstart> selectByExample(AppstartExample example);

    Appstart selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appstart record, @Param("example") AppstartExample example);

    int updateByExample(@Param("record") Appstart record, @Param("example") AppstartExample example);

    int updateByPrimaryKeySelective(Appstart record);

    int updateByPrimaryKey(Appstart record);
}