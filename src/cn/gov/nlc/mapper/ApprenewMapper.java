package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Apprenew;
import cn.gov.nlc.pojo.ApprenewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApprenewMapper {
    int countByExample(ApprenewExample example);

    int deleteByExample(ApprenewExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Apprenew record);

    int insertSelective(Apprenew record);

    List<Apprenew> selectByExample(ApprenewExample example);

    Apprenew selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Apprenew record, @Param("example") ApprenewExample example);

    int updateByExample(@Param("record") Apprenew record, @Param("example") ApprenewExample example);

    int updateByPrimaryKeySelective(Apprenew record);

    int updateByPrimaryKey(Apprenew record);
}