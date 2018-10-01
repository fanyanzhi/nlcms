package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcsubjectcollect;
import cn.gov.nlc.pojo.NlcsubjectcollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcsubjectcollectMapper {
    int countByExample(NlcsubjectcollectExample example);

    int deleteByExample(NlcsubjectcollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcsubjectcollect record);

    int insertSelective(Nlcsubjectcollect record);

    List<Nlcsubjectcollect> selectByExample(NlcsubjectcollectExample example);

    Nlcsubjectcollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcsubjectcollect record, @Param("example") NlcsubjectcollectExample example);

    int updateByExample(@Param("record") Nlcsubjectcollect record, @Param("example") NlcsubjectcollectExample example);

    int updateByPrimaryKeySelective(Nlcsubjectcollect record);

    int updateByPrimaryKey(Nlcsubjectcollect record);
}