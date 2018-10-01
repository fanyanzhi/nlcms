package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnoticecollect;
import cn.gov.nlc.pojo.NlcnoticecollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnoticecollectMapper {
    int countByExample(NlcnoticecollectExample example);

    int deleteByExample(NlcnoticecollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnoticecollect record);

    int insertSelective(Nlcnoticecollect record);

    List<Nlcnoticecollect> selectByExample(NlcnoticecollectExample example);

    Nlcnoticecollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnoticecollect record, @Param("example") NlcnoticecollectExample example);

    int updateByExample(@Param("record") Nlcnoticecollect record, @Param("example") NlcnoticecollectExample example);

    int updateByPrimaryKeySelective(Nlcnoticecollect record);

    int updateByPrimaryKey(Nlcnoticecollect record);
}