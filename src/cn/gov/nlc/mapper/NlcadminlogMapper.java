package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.NlcadminlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcadminlogMapper {
    int countByExample(NlcadminlogExample example);

    int deleteByExample(NlcadminlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcadminlog record);

    int insertSelective(Nlcadminlog record);

    List<Nlcadminlog> selectByExample(NlcadminlogExample example);

    Nlcadminlog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcadminlog record, @Param("example") NlcadminlogExample example);

    int updateByExample(@Param("record") Nlcadminlog record, @Param("example") NlcadminlogExample example);

    int updateByPrimaryKeySelective(Nlcadminlog record);

    int updateByPrimaryKey(Nlcadminlog record);
}