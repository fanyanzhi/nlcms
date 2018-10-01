package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlctrailercollect;
import cn.gov.nlc.pojo.NlctrailercollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlctrailercollectMapper {
    int countByExample(NlctrailercollectExample example);

    int deleteByExample(NlctrailercollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlctrailercollect record);

    int insertSelective(Nlctrailercollect record);

    List<Nlctrailercollect> selectByExample(NlctrailercollectExample example);

    Nlctrailercollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlctrailercollect record, @Param("example") NlctrailercollectExample example);

    int updateByExample(@Param("record") Nlctrailercollect record, @Param("example") NlctrailercollectExample example);

    int updateByPrimaryKeySelective(Nlctrailercollect record);

    int updateByPrimaryKey(Nlctrailercollect record);
}