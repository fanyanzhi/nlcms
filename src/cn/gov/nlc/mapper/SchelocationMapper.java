package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Schelocation;
import cn.gov.nlc.pojo.SchelocationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchelocationMapper {
    int countByExample(SchelocationExample example);

    int deleteByExample(SchelocationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Schelocation record);

    int insertSelective(Schelocation record);

    List<Schelocation> selectByExample(SchelocationExample example);

    Schelocation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Schelocation record, @Param("example") SchelocationExample example);

    int updateByExample(@Param("record") Schelocation record, @Param("example") SchelocationExample example);

    int updateByPrimaryKeySelective(Schelocation record);

    int updateByPrimaryKey(Schelocation record);
}