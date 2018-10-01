package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.pojo.ThirdappExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdappMapper {
    int countByExample(ThirdappExample example);

    int deleteByExample(ThirdappExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Thirdapp record);

    int insertSelective(Thirdapp record);

    List<Thirdapp> selectByExample(ThirdappExample example);

    Thirdapp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Thirdapp record, @Param("example") ThirdappExample example);

    int updateByExample(@Param("record") Thirdapp record, @Param("example") ThirdappExample example);

    int updateByPrimaryKeySelective(Thirdapp record);

    int updateByPrimaryKey(Thirdapp record);
}