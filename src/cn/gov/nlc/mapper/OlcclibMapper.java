package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Olcclib;
import cn.gov.nlc.pojo.OlcclibExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OlcclibMapper {
    int countByExample(OlcclibExample example);

    int deleteByExample(OlcclibExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Olcclib record);

    int insertSelective(Olcclib record);

    List<Olcclib> selectByExample(OlcclibExample example);

    Olcclib selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Olcclib record, @Param("example") OlcclibExample example);

    int updateByExample(@Param("record") Olcclib record, @Param("example") OlcclibExample example);

    int updateByPrimaryKeySelective(Olcclib record);

    int updateByPrimaryKey(Olcclib record);
}