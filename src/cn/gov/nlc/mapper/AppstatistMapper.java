package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Appstatist;
import cn.gov.nlc.pojo.AppstatistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppstatistMapper {
    int countByExample(AppstatistExample example);

    int deleteByExample(AppstatistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appstatist record);

    int insertSelective(Appstatist record);

    List<Appstatist> selectByExample(AppstatistExample example);

    Appstatist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appstatist record, @Param("example") AppstatistExample example);

    int updateByExample(@Param("record") Appstatist record, @Param("example") AppstatistExample example);

    int updateByPrimaryKeySelective(Appstatist record);

    int updateByPrimaryKey(Appstatist record);
}