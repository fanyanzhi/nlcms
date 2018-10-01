package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcsubjectpraise;
import cn.gov.nlc.pojo.NlcsubjectpraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcsubjectpraiseMapper {
    int countByExample(NlcsubjectpraiseExample example);

    int deleteByExample(NlcsubjectpraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcsubjectpraise record);

    int insertSelective(Nlcsubjectpraise record);

    List<Nlcsubjectpraise> selectByExample(NlcsubjectpraiseExample example);

    Nlcsubjectpraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcsubjectpraise record, @Param("example") NlcsubjectpraiseExample example);

    int updateByExample(@Param("record") Nlcsubjectpraise record, @Param("example") NlcsubjectpraiseExample example);

    int updateByPrimaryKeySelective(Nlcsubjectpraise record);

    int updateByPrimaryKey(Nlcsubjectpraise record);
}