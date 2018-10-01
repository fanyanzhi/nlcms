package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Infosetup;
import cn.gov.nlc.pojo.InfosetupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfosetupMapper {
    int countByExample(InfosetupExample example);

    int deleteByExample(InfosetupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Infosetup record);

    int insertSelective(Infosetup record);

    List<Infosetup> selectByExample(InfosetupExample example);

    Infosetup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Infosetup record, @Param("example") InfosetupExample example);

    int updateByExample(@Param("record") Infosetup record, @Param("example") InfosetupExample example);

    int updateByPrimaryKeySelective(Infosetup record);

    int updateByPrimaryKey(Infosetup record);
}