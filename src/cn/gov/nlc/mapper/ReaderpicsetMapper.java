package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Readerpicset;
import cn.gov.nlc.pojo.ReaderpicsetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReaderpicsetMapper {
    int countByExample(ReaderpicsetExample example);

    int deleteByExample(ReaderpicsetExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Readerpicset record);

    int insertSelective(Readerpicset record);

    List<Readerpicset> selectByExample(ReaderpicsetExample example);

    Readerpicset selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Readerpicset record, @Param("example") ReaderpicsetExample example);

    int updateByExample(@Param("record") Readerpicset record, @Param("example") ReaderpicsetExample example);

    int updateByPrimaryKeySelective(Readerpicset record);

    int updateByPrimaryKey(Readerpicset record);
}