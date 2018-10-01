package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.pojo.NlcadsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcadsMapper {
    int countByExample(NlcadsExample example);

    int deleteByExample(NlcadsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcads record);

    int insertSelective(Nlcads record);

    List<Nlcads> selectByExample(NlcadsExample example);

    Nlcads selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcads record, @Param("example") NlcadsExample example);

    int updateByExample(@Param("record") Nlcads record, @Param("example") NlcadsExample example);

    int updateByPrimaryKeySelective(Nlcads record);

    int updateByPrimaryKey(Nlcads record);
}