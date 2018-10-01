package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Useralert;
import cn.gov.nlc.pojo.UseralertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UseralertMapper {
    int countByExample(UseralertExample example);

    int deleteByExample(UseralertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Useralert record);

    int insertSelective(Useralert record);

    List<Useralert> selectByExample(UseralertExample example);

    Useralert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Useralert record, @Param("example") UseralertExample example);

    int updateByExample(@Param("record") Useralert record, @Param("example") UseralertExample example);

    int updateByPrimaryKeySelective(Useralert record);

    int updateByPrimaryKey(Useralert record);
}