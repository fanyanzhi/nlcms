package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Olcchotword;
import cn.gov.nlc.pojo.OlcchotwordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OlcchotwordMapper {
    int countByExample(OlcchotwordExample example);

    int deleteByExample(OlcchotwordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Olcchotword record);

    int insertSelective(Olcchotword record);

    List<Olcchotword> selectByExample(OlcchotwordExample example);

    Olcchotword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Olcchotword record, @Param("example") OlcchotwordExample example);

    int updateByExample(@Param("record") Olcchotword record, @Param("example") OlcchotwordExample example);

    int updateByPrimaryKeySelective(Olcchotword record);

    int updateByPrimaryKey(Olcchotword record);
}