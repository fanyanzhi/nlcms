package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.pojo.NlctemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlctemplateMapper {
    int countByExample(NlctemplateExample example);

    int deleteByExample(NlctemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlctemplate record);

    int insertSelective(Nlctemplate record);

    List<Nlctemplate> selectByExample(NlctemplateExample example);

    Nlctemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlctemplate record, @Param("example") NlctemplateExample example);

    int updateByExample(@Param("record") Nlctemplate record, @Param("example") NlctemplateExample example);

    int updateByPrimaryKeySelective(Nlctemplate record);

    int updateByPrimaryKey(Nlctemplate record);
}