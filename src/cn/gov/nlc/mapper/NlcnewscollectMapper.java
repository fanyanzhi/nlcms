package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnewscollect;
import cn.gov.nlc.pojo.NlcnewscollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnewscollectMapper {
    int countByExample(NlcnewscollectExample example);

    int deleteByExample(NlcnewscollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnewscollect record);

    int insertSelective(Nlcnewscollect record);

    List<Nlcnewscollect> selectByExample(NlcnewscollectExample example);

    Nlcnewscollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnewscollect record, @Param("example") NlcnewscollectExample example);

    int updateByExample(@Param("record") Nlcnewscollect record, @Param("example") NlcnewscollectExample example);

    int updateByPrimaryKeySelective(Nlcnewscollect record);

    int updateByPrimaryKey(Nlcnewscollect record);
}