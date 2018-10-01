package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Scheappstart;
import cn.gov.nlc.pojo.ScheappstartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheappstartMapper {
    int countByExample(ScheappstartExample example);

    int deleteByExample(ScheappstartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Scheappstart record);

    int insertSelective(Scheappstart record);

    List<Scheappstart> selectByExample(ScheappstartExample example);

    Scheappstart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Scheappstart record, @Param("example") ScheappstartExample example);

    int updateByExample(@Param("record") Scheappstart record, @Param("example") ScheappstartExample example);

    int updateByPrimaryKeySelective(Scheappstart record);

    int updateByPrimaryKey(Scheappstart record);
}