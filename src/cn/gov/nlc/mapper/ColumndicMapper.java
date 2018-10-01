package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Columndic;
import cn.gov.nlc.pojo.ColumndicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ColumndicMapper {
    int countByExample(ColumndicExample example);

    int deleteByExample(ColumndicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Columndic record);

    int insertSelective(Columndic record);

    List<Columndic> selectByExample(ColumndicExample example);

    Columndic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Columndic record, @Param("example") ColumndicExample example);

    int updateByExample(@Param("record") Columndic record, @Param("example") ColumndicExample example);

    int updateByPrimaryKeySelective(Columndic record);

    int updateByPrimaryKey(Columndic record);
}