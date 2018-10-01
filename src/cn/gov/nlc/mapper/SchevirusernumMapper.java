package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Schevirusernum;
import cn.gov.nlc.pojo.SchevirusernumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchevirusernumMapper {
    int countByExample(SchevirusernumExample example);

    int deleteByExample(SchevirusernumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Schevirusernum record);

    int insertSelective(Schevirusernum record);

    List<Schevirusernum> selectByExample(SchevirusernumExample example);

    Schevirusernum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Schevirusernum record, @Param("example") SchevirusernumExample example);

    int updateByExample(@Param("record") Schevirusernum record, @Param("example") SchevirusernumExample example);

    int updateByPrimaryKeySelective(Schevirusernum record);

    int updateByPrimaryKey(Schevirusernum record);
}